package org.kemri.wellcome.dhisreport.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hisp.dhis.dxf2.importsummary.ImportConflict;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.AutoExecuteReport;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.db.DHIS2ReportingDAO;
import org.kemri.wellcome.dhisreport.api.db.DhisServerDAO;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValue;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValueSet;
import org.kemri.wellcome.dhisreport.api.model.DataValueTemplate;
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
		                if ( value != null ){
		                    dataValue.setValue( value );
		                    dataValues.add( dataValue );
		                }
		            }catch ( DHIS2ReportingException ex ){
		                log.error(ex.getMessage());
		            }            
		        }
		        
		        ImportSummary importSummary=null;
				try {
					importSummary = serverDAO.getDhisServer().postReport( dataValueSet );;
				} catch (DHIS2ReportingException e) {
					log.error(e.getMessage());
				}
				importSummary.setReportName(report.getName());
				importSummary.setReportDate(DateUtils.stringToDate(DateUtils.getToday()));
				importSummary.getDataValueCount().setImportSummary(importSummary);
				importSummary.getDataValueCount().setUid(UUID.randomUUID().toString());
				List<ImportConflict> conflicts = new ArrayList<ImportConflict>();
				for(ImportConflict conflict:importSummary.getConflicts()){
					conflict.setImportSummary(importSummary);
					conflict.setUid(UUID.randomUUID().toString());
					conflicts.add(conflict);
				}
				importSummary.setConflicts(conflicts);
				importSummary = dao.saveImportSummary(importSummary);
				
			}//end location loop			
		}//end report definition report
	}

}
