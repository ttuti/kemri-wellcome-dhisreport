/**
 *  Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of DHIS2 Reporting module.
 *
 *  DHIS2 Reporting module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  DHIS2 Reporting module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with DHIS2 Reporting module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package org.kemri.wellcome.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hisp.dhis.dxf2.importsummary.ImportConflict;
import org.hisp.dhis.dxf2.importsummary.ImportCount;
import org.hisp.dhis.dxf2.importsummary.ImportStatus;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.model.JqGridFilter;
import org.kemri.wellcome.dhisreport.api.model.JqGridObjectMapper;
import org.kemri.wellcome.dhisreport.api.model.JqgridResponse;
import org.kemri.wellcome.dhisreport.api.model.Location;
import org.kemri.wellcome.dhisreport.api.model.LocationMapper;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinition;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinitionMapper;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingService;
import org.kemri.wellcome.dhisreport.api.DhisServerService;
import org.kemri.wellcome.dhisreport.api.dto.LocationDTO;
import org.kemri.wellcome.dhisreport.api.dto.ReportDefinitionDTO;
import org.kemri.wellcome.dhisreport.api.dto.ReportExecute;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValue;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValueSet;
import org.kemri.wellcome.dhisreport.api.utils.DateUtils;
import org.kemri.wellcome.dhisreport.api.utils.MonthlyPeriod;
import org.kemri.wellcome.dhisreport.api.utils.Period;
import org.kemri.wellcome.dhisreport.api.utils.WeeklyPeriod;
import org.kemri.wellcome.dhisreport.repositories.LocationRepository;
import org.kemri.wellcome.dhisreport.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


/**
 * The main controller.
 */
@Controller
@RequestMapping(Views.REPORT)
public class ReportController
{
	@Autowired
	private DHIS2ReportingService service;
	
	@Autowired
	private DhisServerService serverService;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	protected final Logger log = Logger.getLogger(ReportController.class);

    @RequestMapping( value = Views.LIST_REPORT, method = RequestMethod.GET )
    public String listReports(){
    	return Views.LIST_REPORT;
    }
    
    @RequestMapping( value = Views.LIST_LOCATION, method = RequestMethod.GET )
    public String listLocations(){
    	return Views.LIST_LOCATION;
    }
    
    @RequestMapping(value=Views.ALLREPORTS,produces= Views.MEDIA_TYPE_JSON)
	public @ResponseBody JqgridResponse<ReportDefinitionDTO> records(
			@RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,		     
			@RequestParam(value="page", required=false, defaultValue="1") Integer page,														 
			@RequestParam(value="rows", required=false, defaultValue="10") Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
		    @RequestParam(value="sord", required=false) String sord
			) {
		
		Pageable pageRequest = new PageRequest(page-1, rows);
		
		
		if (search == true) {
			return getFilteredReports(filters, pageRequest);
		}
		Page<ReportDefinition> reports = reportRepository.findAll(pageRequest);
		List<ReportDefinitionDTO> reportsDTOList = ReportDefinitionMapper.map(reports);

		JqgridResponse<ReportDefinitionDTO> response = new JqgridResponse<ReportDefinitionDTO>();
		response.setRows(reportsDTOList);
		response.setRecords(Long.valueOf(reports.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(reports.getTotalPages()).toString());
		response.setPage(Integer.valueOf(reports.getNumber()+1).toString());

		return response;
	}
    
    @RequestMapping(value=Views.ALLLOCATIONS,produces= Views.MEDIA_TYPE_JSON)
	public @ResponseBody JqgridResponse<LocationDTO> locationRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,		     
			@RequestParam(value="page", required=false, defaultValue="1") Integer page,														 
			@RequestParam(value="rows", required=false, defaultValue="10") Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
		    @RequestParam(value="sord", required=false) String sord
			) {
		
		Pageable pageRequest = new PageRequest(page-1, rows);
		
		
		if (search == true) {
			return getFilteredLocations(filters, pageRequest);
		}
		Page<Location> locations = locationRepository.findAll(pageRequest);
		List<LocationDTO> locationsDTOList = LocationMapper.map(locations);

		JqgridResponse<LocationDTO> response = new JqgridResponse<LocationDTO>();
		response.setRows(locationsDTOList);
		response.setRecords(Long.valueOf(locations.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(locations.getTotalPages()).toString());
		response.setPage(Integer.valueOf(locations.getNumber()+1).toString());

		return response;
	}

    @RequestMapping( value = Views.SETUP_REPORT, method = RequestMethod.GET )
    public String setupReport( Model model, @RequestParam( value = "id", required = false )
    Integer reportDefinition_id )
    {
    	ReportExecute reportExecute = new ReportExecute();
    	reportExecute.setReportDefinationId(reportDefinition_id);
    	reportExecute.setReportDefinitionName(service.getReportDefinition(reportDefinition_id).getName());
        model.addAttribute( "locations", service.getAllLocations() );
        model.addAttribute("reportExecute", reportExecute);
        return Views.SETUP_REPORT;
    }

    @RequestMapping( value = Views.SETUP_REPORT, method = RequestMethod.POST )
    public @ResponseBody Map<String, ? extends Object> setupReport(
    	   @RequestBody ReportExecute reportExecute){
        Period period;
        Date reportDate = null;
		try{
			reportDate = new SimpleDateFormat( "yyyy-MM-dd" ).parse( reportExecute.getDate() );
		}catch(ParseException e){
			log.error(e.getMessage());
			return Collections.singletonMap("u", e.getMessage());
		}
        
        if(reportExecute.getFrequency().equalsIgnoreCase("Monthly")){
        	period = new MonthlyPeriod( reportDate );        	
        }else{
        	period = new WeeklyPeriod( reportDate );
        }
        
        //prepare import summary
        ImportSummary importSummary = new ImportSummary();  
    	importSummary.setStatus(ImportStatus.ERROR);
    	importSummary.setUid(UUID.randomUUID().toString());
    	importSummary.setName(reportExecute.getReportDefinitionName());
		importSummary.setDescription("Error posting to DHISv2. No matching parameters on the server");
		importSummary.setReportDate(DateUtils.stringToDate(DateUtils.getToday())); 
		importSummary.setDataSetComplete("false");
				
		// Get Location by OrgUnit Code
        ReportDefinition report = service.getReportDefinition( reportExecute.getReportDefinationId());
        Location location = service.getLocationByOU_Code( reportExecute.getLocation() );
        DataValueSet dvs = service.evaluateReportDefinition( report,period, location );
        if(dvs.getError()!=null && dvs.getError().equalsIgnoreCase("ERROR")){
        	StringBuilder builder = new StringBuilder();
        	for(DataValue dv : dvs.getDataValues()){        		
        		builder.append(dv.getError());
        		builder.append(";");
        	}
        	importSummary.setReportName(reportExecute.getReportDefinitionName());  
        	importSummary.setStatus(ImportStatus.ERROR);
        	importSummary.setDescription(builder.toString());
        	createImportCount(importSummary);
        	log.info("User:"+service.getUsername()+" executed report template :"+importSummary.getReportName()+" on "+Calendar.getInstance().getTime());
        	return Collections.singletonMap("importSummary", importSummary);
        }else{            
            dvs.setOrgUnit( reportExecute.getLocation() );
    		try {
    			importSummary = service.postDataValueSet( dvs );
    		} catch (DHIS2ReportingException e) {
    			log.error(e.getMessage());
    			importSummary.setReportName(reportExecute.getReportDefinitionName());  
    			importSummary.setStatus(ImportStatus.ERROR);
    			importSummary.setDescription(e.getMessage());
    			createImportCount(importSummary);
    			log.info("User:"+service.getUsername()+" executed report template :"+importSummary.getReportName()+" on "+Calendar.getInstance().getTime());
    			return Collections.singletonMap("importSummary", importSummary);
    		}
    		importSummary.setReportName(reportExecute.getReportDefinitionName());    		
    		List<ImportConflict> conflicts = new ArrayList<ImportConflict>();
    		if(importSummary.getConflicts() !=null){
    			for(ImportConflict conflict:importSummary.getConflicts()){
    				conflict.setImportSummary(importSummary);
    				conflict.setUid(UUID.randomUUID().toString());
    				conflict.setName(UUID.randomUUID().toString());
    				conflicts.add(conflict);
    			}	
    			importSummary.setConflicts(conflicts);				
    		}
    		importSummary.setReportDate(DateUtils.stringToDate(DateUtils.getToday()));
    		importSummary = service.saveImportSummary(importSummary);
    		log.info("User:"+service.getUsername()+" executed report template :"+importSummary.getReportName()+" on "+Calendar.getInstance().getTime());
    		return Collections.singletonMap("importSummary", importSummary);
        }                
    }
    
    
    public JqgridResponse<ReportDefinitionDTO> getFilteredReports(String filters,
			Pageable pageRequest) {
		String qCode = null;
		String qName = null;		

		JqGridFilter jqgridFilter = JqGridObjectMapper.map(filters);
		for (JqGridFilter.Rule rule : jqgridFilter.getRules()) {
			if (rule.getField().equals("name"))
				qName =  rule.getData();
			else if (rule.getField().equals("code"))
				qCode = rule.getData();
		}

		Page<ReportDefinition> reports = null;
		if (qName != null)
			reports = reportRepository.findByNameLike("%"+qName+"%", pageRequest);
		if (qCode != null)
			reports = reportRepository.findByCodeLike("%"+qCode+"%",pageRequest);
		if (qCode != null && qName !=null)
			reports = reportRepository.findByNameLikeAndCodeLike("%"+qName+"%","%"+qCode+"%", pageRequest);		

		List<ReportDefinitionDTO> reportDTOList = ReportDefinitionMapper.map(reports);
		JqgridResponse<ReportDefinitionDTO> response = new JqgridResponse<ReportDefinitionDTO>();
		response.setRows(reportDTOList);
		response.setRecords(Long.valueOf(reports.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(reports.getTotalPages()).toString());
		response.setPage(Integer.valueOf(reports.getNumber() + 1).toString());
		return response;
	}
    
    public JqgridResponse<LocationDTO> getFilteredLocations(String filters,
			Pageable pageRequest) {
		String qCode = null;
		String qName = null;		

		JqGridFilter jqgridFilter = JqGridObjectMapper.map(filters);
		for (JqGridFilter.Rule rule : jqgridFilter.getRules()) {
			if (rule.getField().equals("name"))
				qName =  rule.getData();
			else if (rule.getField().equals("code"))
				qCode = rule.getData();
		}

		Page<Location> locations = null;
		if (qName != null)
			locations = locationRepository.findByNameLike("%"+qName+"%", pageRequest);
		if (qCode != null)
			locations = locationRepository.findByCodeLike("%"+qCode+"%",pageRequest);
		if (qCode != null && qName !=null)
			locations = locationRepository.findByNameLikeAndCodeLike("%"+qName+"%","%"+qCode+"%", pageRequest);		

		List<LocationDTO> locationDTOList = LocationMapper.map(locations);
		JqgridResponse<LocationDTO> response = new JqgridResponse<LocationDTO>();
		response.setRows(locationDTOList);
		response.setRecords(Long.valueOf(locations.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(locations.getTotalPages()).toString());
		response.setPage(Integer.valueOf(locations.getNumber() + 1).toString());
		return response;
	}
    
    @RequestMapping( value = Views.ADD_LOCATION, method = RequestMethod.GET )
    public String addLocation( Model model ){    	
    	model.addAttribute("location", new Location()); 	      
    	return Views.ADD_LOCATION;
    }
    
    @RequestMapping(value = Views.ADD_LOCATION, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addLocation(
			@RequestBody Location location){
    	
    	if(service.getLocationByOU_Code(location.getName())==null){
    		service.saveLocation(location);	  
    		log.info("User:"+service.getUsername()+" added location :"+location.getName()+" on "+Calendar.getInstance().getTime());
        	return Collections.singletonMap("u", "Saved");
    	}
    	return Collections.singletonMap("u", "Location already exists");	
	}
    
    @RequestMapping( value = Views.EDIT_LOCATION, method = RequestMethod.GET )
    public String editLocation( Model model, @RequestParam( value = "id", required = false )
    Integer location_id )
    {
    	model.addAttribute( "location", service.getLocation(location_id) );
        return Views.EDIT_LOCATION;
    }
    
    @RequestMapping( value = Views.SUMMARY_DETAILS, method = RequestMethod.GET )
    public String summaryDetails( Model model, @RequestParam( value = "id", required = false )
    Integer summary_id )
    {
    	model.addAttribute( "summary", service.getImportSummary(summary_id) );
    	model.addAttribute( "count", service.getImportSummary(summary_id).getDataValueCount().toString() );
    	return Views.SUMMARY_DETAILS;
    }
    
    @RequestMapping(value = Views.EDIT_LOCATION, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> editLocation(
			@RequestBody Location location){
    	
    	service.saveLocation(location);
    	log.info("User:"+service.getUsername()+" edited location :"+location.getName()+" on "+Calendar.getInstance().getTime());
    	return Collections.singletonMap("u", "Saved");	
	}
    
    @RequestMapping( value = Views.DELETE_LOCATION, method = RequestMethod.GET )
    public String deleteLocation( Model model,
        @RequestParam( value = "id", required = false )
        Integer location_id)
    {
        Location ln = service.getLocation(location_id);
        log.info("User:"+service.getUsername()+" deleted location :"+ln.getName()+" on "+Calendar.getInstance().getTime());
        service.purgeLocation(ln);
        return Views.LIST_LOCATION;
    }
    
    private void createImportCount(ImportSummary importSummary){
    	ImportCount importCount = new ImportCount();
    	importCount.setIgnored(0);
    	importCount.setImported(0);
    	importCount.setUpdated(0);
    	importCount.setUid(UUID.randomUUID().toString());
    	importCount.setName(importSummary.getReportName());
    	importSummary.setDataValueCount(importCount);
    	service.saveImportSummary(importSummary);
    }
	
}
