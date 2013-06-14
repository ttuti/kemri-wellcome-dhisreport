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

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingService;
import org.kemri.wellcome.dhisreport.api.model.DataValueTemplate;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinition;
import org.kemri.wellcome.dhisreport.api.model.ReportTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * The main controller.
 */
@Controller
@RequestMapping(Views.REPORT_DEFINITION)
public class ReportDefinitionController
{
	
	@Autowired
	private DHIS2ReportingService service;
	
	@Autowired
	private Validator validator;
	
	protected final Logger log = Logger.getLogger( ReportDefinitionController.class );

    @RequestMapping( value = Views.UPLOAD, method = RequestMethod.GET )
    public String upload(Model model){
    	model.addAttribute("username", service.getUsername());
    	return Views.UPLOAD;
    }
    
    @RequestMapping(value = Views.UPLOAD, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> upload(
		   @RequestParam("file") MultipartFile file, 
			Model model) throws IOException{
    	InputStream is = file.getInputStream();
        try{
        	service.unMarshallandSaveReportTemplates( is );
        }catch ( Exception ex ){
        	log.error(ex.getMessage());
        	ex.printStackTrace();
        } finally{
            is.close();
        }
        log.info("User:"+service.getUsername()+" uploaded the report template :"+file.getOriginalFilename()+" on "+Calendar.getInstance().getTime());
        return Collections.singletonMap("u", "Saved");
	} 

    @RequestMapping( value = Views.EXPORT, method = RequestMethod.GET )
    public void export(HttpServletResponse response )
        throws Exception
    {
        response.setContentType( "application/xml" );
        response.setCharacterEncoding( "UTF-8" );
        response.addHeader( "Content-Disposition", "attachment; filename=reportDefinition.xml" );
        ReportTemplates templates = service.getReportTemplates();
        if(templates !=null)
        	service.marshallReportTemplates( response.getOutputStream(), templates );
    }

    @RequestMapping( value = Views.EDIT_REPORT_DEF, method = RequestMethod.GET )
    public String editReportDefinition( Model model, @RequestParam( value = "id", required = false )
    Integer reportDefinition_id )
    {
    	model.addAttribute( "reportDefinition", service.getReportDefinition( reportDefinition_id ) );
        return Views.EDIT_REPORT_DEF;
    }
    
    @RequestMapping( value = Views.DELETE_REPORT_DEF, method = RequestMethod.GET )
    public String deleteReportDefinition( Model model,
        @RequestParam( value = "id", required = false )
        Integer reportDefinition_id)
    {
        ReportDefinition rd = service.getReportDefinition( reportDefinition_id );
        for(DataValueTemplate dvt: rd.getDataValueTemplates()){
        	service.purgeDataValueTemplate(dvt);
        }
        log.info("User:"+service.getUsername()+" deleted report template :"+rd.getName()+" on "+Calendar.getInstance().getTime());
        service.purgeReportDefinition( rd );        
        return Views.LIST_REPORT;
    }

    @RequestMapping( value = Views.EDIT_DATA_VAL_TEMP, method = RequestMethod.GET )
    public String editDataValueTemplate( Model model,
        @RequestParam( value = "dataValueTemplate_id", required = false )
        Integer dataValueTemplate_id, @RequestParam( value = "dataValueTemplate_query", required = false )
        String dataValueTemplate_query )
    {
        DataValueTemplate dvt = service.getDataValueTemplate( dataValueTemplate_id );
        dvt.setQuery( dataValueTemplate_query );
        log.info("User:"+service.getUsername()+" updated data value template :"+dvt.getId()+":"+dataValueTemplate_query+" on "+Calendar.getInstance().getTime());
        service.saveDataValueTemplate( dvt );

        model.addAttribute( "dataValueTemplate", dvt );
        return "redirect:/"+Views.REPORT_DEFINITION+"/"+Views.EDIT_REPORT_DEF+Views.EXT+"?id="
            + dvt.getReportDefinition().getId();
    }

}
