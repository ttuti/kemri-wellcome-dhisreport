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

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingService;
import org.kemri.wellcome.dhisreport.api.DhisServerService;
import org.kemri.wellcome.dhisreport.api.dto.DhisServerDTO;
import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The main controller.
 */
@Controller
@RequestMapping(Views.DHIS)
public class Dhis2ServerController
{
	@Autowired
	private DHIS2ReportingService service;
	
	@Autowired
	private DhisServerService serverService;
	
	@Autowired
	private Validator validator;
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	private static final Logger log = Logger.getLogger(Dhis2ServerController.class);

    @RequestMapping( value = Views.CONFIGURE_DHISv2, method = RequestMethod.GET )
    public String configServer( Model model ){
    	
    	HttpDhis2Server dhisServer = serverService.getDhisServer();
    	DhisServerDTO server = new DhisServerDTO();
    	if(dhisServer == null){
    		log.error("\n Server is null \n");    		
            model.addAttribute( "dhis2Server", server );
    	}else{
    		log.error("\n Server not null \n");
    		server.setId(dhisServer.getId());
    		server.setUrl(dhisServer.getsUrl());
    		server.setUsername(dhisServer.getUsername());
    		server.setPassword(dhisServer.getPassword());
            model.addAttribute( "dhis2Server", server );
    	}        
    	return Views.CONFIGURE_DHISv2;
    }
    
    @RequestMapping(value = Views.CONFIGURE_DHISv2, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> configServer(
			@RequestBody DhisServerDTO dhis2Server) throws Exception {
    	
    	Set<ConstraintViolation<DhisServerDTO>> failures = validator.validate(dhis2Server);
		if (!failures.isEmpty()) {
			return Collections.singletonMap("u", validationMessages(failures).toString());
		} else{
			HttpDhis2Server server=null;
	        URL url = new URL( dhis2Server.getUrl() );
	        HttpDhis2Server objServer = serverService.getDhisServer();
	        if(objServer==null){
	        	server = new HttpDhis2Server();
	        	server.setUrl( url );
		        server.setsUrl(url);
		        server.setUsername( dhis2Server.getUsername() );
		        server.setPassword( dhis2Server.getPassword() );
		        serverService.updateDhisServer(server);
	        }else{
	        	objServer.setUrl( url );
	        	objServer.setsUrl(url);
	        	objServer.setUsername( dhis2Server.getUsername() );
	        	objServer.setPassword( dhis2Server.getPassword() );
	        	serverService.updateDhisServer(objServer);
	        }

	        log.debug( "Dhis2 server configured: " + dhis2Server.getUsername() + ":xxxxxx  " + url.toExternalForm() );
	        return Collections.singletonMap("u", "Saved");
		}	
	}    
    
    /* Validation Messages */
	private Map<String, String> validationMessages(
			Set<ConstraintViolation<DhisServerDTO>> failures) 
	{
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<DhisServerDTO> failure : failures) 
		{
			failureMessages.put(failure.getPropertyPath().toString(), 
								failure.getMessage());
		}
		return failureMessages;
	}
}
