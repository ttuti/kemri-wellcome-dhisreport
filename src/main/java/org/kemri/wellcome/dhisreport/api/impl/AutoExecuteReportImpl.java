package org.kemri.wellcome.dhisreport.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hisp.dhis.dxf2.importsummary.ImportConflict;
import org.hisp.dhis.dxf2.importsummary.ImportCount;
import org.hisp.dhis.dxf2.importsummary.ImportStatus;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.AutoExecuteReport;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.db.DHIS2ReportingDAO;
import org.kemri.wellcome.dhisreport.api.db.DhisServerDAO;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValue;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValueSet;
import org.kemri.wellcome.dhisreport.api.model.DataValueTemplate;
import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;
import org.kemri.wellcome.dhisreport.api.model.Location;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinition;
import org.kemri.wellcome.dhisreport.api.utils.MonthlyPeriod;
import org.kemri.wellcome.dhisreport.api.utils.Period;
import org.kemri.wellcome.dhisreport.api.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("syncPost")
public class AutoExecuteReportImpl implements AutoExecuteReport {
	
	protected final Logger log = Logger.getLogger(AutoExecuteReportImpl.class);
	
	@Autowired
    private DHIS2ReportingDAO dao;
    
    @Autowired
	private DhisServerDAO serverDAO;
	
	@Override
	public void postToDhis() {
		Period period = new MonthlyPeriod( DateUtils.stringToDate(DateUtils.getToday()));
		List<ReportDefinition> reportList = (List<ReportDefinition>)dao.getAllReportDefinitions();
		List<Location> locationList = (List<Location>)dao.getAllLocations(); 
		HttpDhis2Server server = serverDAO.getDhisServer();
		
		//process all reports
		for(ReportDefinition report : reportList){
			for(Location location : locationList){				
				//execute each report for each location
				Collection<DataValueTemplate> templates = report.getDataValueTemplates();
		        DataValueSet dataValueSet = new DataValueSet();
		        dataValueSet.setDataElementIdScheme( "code" );
		        dataValueSet.setOrgUnitIdScheme( "code" );
		        dataValueSet.setOrgUnit(location.getName());
		        dataValueSet.setPeriod( period.getAsIsoString() );
		        dataValueSet.setDataSet( report.getCode() );

		        Collection<DataValue> dataValues = dataValueSet.getDataValues();

		        for ( DataValueTemplate dvt : templates ){
		            DataValue dataValue = new DataValue();
		            dataValue.setDataElement( dvt.getDataelement().getCode() );
		            dataValue.setCategoryOptionCombo( dvt.getDisaggregation().getCode() );
		            try{
		                String value = dao.evaluateDataValueTemplate( dvt, period, location );
		                if ( value != null && !value.equalsIgnoreCase("No results returned for query")){
		                    dataValue.setValue( value );
		                    dataValues.add( dataValue );
		                }
		            }catch ( DHIS2ReportingException ex ){
		                log.error(ex.getMessage());
		            }            
		        }		        
		        ImportSummary importSummary=new ImportSummary();
		        importSummary.setReportName(report.getName());
		        importSummary.setUid(UUID.randomUUID().toString());
				importSummary.setReportDate(DateUtils.stringToDate(DateUtils.getToday()));
				importSummary.setDataSetComplete("false");
				try {
					int count = 0;
					StringBuilder builder = new StringBuilder();
					log.error("\n Cron AutoExecute Report "+ DateUtils.getToday()+"\n");
					for(DataValue dv : dataValueSet.getDataValues()){
						log.error("\n "+dv.getDataElement()+":"+dv.getValue()+"\n");
						if(dv.getValue().equalsIgnoreCase("No results returned for query")){
							builder.append(dv.getDataElement());
							builder.append(":");
							builder.append(dv.getValue());
							builder.append("\n");
							count +=1;
						}							
					}	
					if(count == dataValueSet.getDataValues().size()){
						importSummary.setStatus(ImportStatus.ERROR);
						importSummary.setDescription(builder.toString());
						createImportCount(importSummary);
					}else{
						importSummary = server.postReport( dataValueSet );
						importSummary.getDataValueCount().setUid(UUID.randomUUID().toString());
						List<ImportConflict> conflicts = new ArrayList<ImportConflict>();
						for(ImportConflict conflict:importSummary.getConflicts()){
							conflict.setImportSummary(importSummary);
							conflict.setUid(UUID.randomUUID().toString());
							conflicts.add(conflict);
						}
						importSummary.setReportName(report.getName());
						importSummary.setConflicts(conflicts);
						importSummary.setReportDate(DateUtils.stringToDate(DateUtils.getToday()));
						dao.saveImportSummary(importSummary);
					}					
				} catch (DHIS2ReportingException e) {
					log.error(e.getMessage());
				}				
			}//end location loop			
		}//end report definition report
	}
	
	private void createImportCount(ImportSummary importSummary){
    	ImportCount importCount = new ImportCount();
    	importCount.setIgnored(0);
    	importCount.setImported(0);
    	importCount.setUpdated(0);
    	importCount.setUid(UUID.randomUUID().toString());
    	importCount.setName(importSummary.getReportName());
    	importSummary.setDataValueCount(importCount);
    	dao.saveImportSummary(importSummary);
    }

}
