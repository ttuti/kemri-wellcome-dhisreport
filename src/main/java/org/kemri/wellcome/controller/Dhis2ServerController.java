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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingService;
import org.kemri.wellcome.dhisreport.api.DhisServerService;
import org.kemri.wellcome.dhisreport.api.dto.DatabaseProperties;
import org.kemri.wellcome.dhisreport.api.dto.DhisServerDTO;
import org.kemri.wellcome.dhisreport.api.dto.UserDTO;
import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;
import org.kemri.wellcome.dhisreport.api.model.JqGridFilter;
import org.kemri.wellcome.dhisreport.api.model.JqGridObjectMapper;
import org.kemri.wellcome.dhisreport.api.model.UserMapper;
import org.kemri.wellcome.dhisreport.api.model.JqgridResponse;
import org.kemri.wellcome.dhisreport.api.model.Role;
import org.kemri.wellcome.dhisreport.api.model.User;
import org.kemri.wellcome.dhisreport.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

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
	
	@Autowired
	private UserRepository userRepository;
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	String databaseProperties = "/WEB-INF/mysql.jdbc.properties";
	
	private static final Logger log = Logger.getLogger(Dhis2ServerController.class);
	
	@RequestMapping(value = Views.LIST_USER, method = RequestMethod.GET)
	public String users() {
		return Views.LIST_USER;
	}
	
	@RequestMapping(value=Views.ALLUSERS,produces= Views.MEDIA_TYPE_JSON)
	public @ResponseBody JqgridResponse<UserDTO> records(
			@RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,		     
			@RequestParam(value="page", required=false, defaultValue="1") Integer page,														 
			@RequestParam(value="rows", required=false, defaultValue="10") Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
		    @RequestParam(value="sord", required=false) String sord
			) {
		
		Sort sort = new Sort(new Order(Direction.DESC,"id"));
		Pageable pageRequest = new PageRequest(page-1, rows,sort);		
		
		if (search == true) {
			return getFilteredUsers(filters, pageRequest);
		}
		
		Page<User> users = userRepository.findAll(pageRequest);
		List<UserDTO> userDTOList = UserMapper.map(users);

		JqgridResponse<UserDTO> response = new JqgridResponse<UserDTO>();
		response.setRows(userDTOList);
		response.setRecords(Long.valueOf(users.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(users.getTotalPages()).toString());
		response.setPage(Integer.valueOf(users.getNumber()+1).toString());

		return response;
	}
	
	public JqgridResponse<UserDTO> getFilteredUsers(String filters,
			Pageable pageRequest) {
		String qUsername = null;
		String qEmail = null;
		Integer qStatus = null;

		JqGridFilter jqgridFilter = JqGridObjectMapper.map(filters);
		for (JqGridFilter.Rule rule : jqgridFilter.getRules()) {
			if (rule.getField().equals("email"))
				qEmail =  rule.getData();
			else if (rule.getField().equals("status"))
				qStatus = Integer.parseInt(rule.getData());
			else if (rule.getField().equals("username"))
				qUsername = rule.getData();
		}

		Page<User> users = null;
		if (qStatus != null){
			users = userRepository.findByEnabled(qStatus, pageRequest);
		}
		if (qUsername != null){
			users = userRepository.findByUsername(qUsername, pageRequest);
		}			
		if (qEmail != null){
			users = userRepository.findByEmail(qEmail,pageRequest);
		}
		

		List<UserDTO> userDTOList = UserMapper.map(users);
		JqgridResponse<UserDTO> response = new JqgridResponse<UserDTO>();
		response.setRows(userDTOList);
		response.setRecords(Long.valueOf(users.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(users.getTotalPages()).toString());
		response.setPage(Integer.valueOf(users.getNumber() + 1).toString());
		return response;
	}
	
	@RequestMapping( value = Views.ADD_USER, method = RequestMethod.GET )
    public String addUser(Model model ){
		model.addAttribute("roleList", service.getAllRoles());
		model.addAttribute("user", new UserDTO());
		return Views.ADD_USER;
	}
	
	@RequestMapping( value = Views.ADD_USER, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> addUser(@RequestBody UserDTO userDTO ){
		if(userDTO.getRoles().isEmpty()||userDTO.getRoles()==null)
			return Collections.singletonMap("u", "Please enter the user roles");
		if(!userDTO.getPassword().equals(userDTO.getPassword2()))
			return Collections.singletonMap("u", "Passwords do not match");
		if(service.getUserByEmail(userDTO.getEmail())!=null)
			return Collections.singletonMap("u", "User with that email address already exists");
		if(service.getUserByUsername(userDTO.getUsername())!=null)
			return Collections.singletonMap("u", "User with that username already exists");
		
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setPassword(encoder.encodePassword(userDTO.getPassword(), null));
		user.setSurname(userDTO.getSurname());
		user.setUsername(userDTO.getUsername());
		user.setUid(UUID.randomUUID().toString());
		List<Role> roleList = new ArrayList<Role>();
		if (!userDTO.getRoles().startsWith("[")) {
			Role role = service.getRoleByName(userDTO.getRoles());
			roleList.add(role);
		} else {
			JSONArray rolesArray=null;
			try {
				rolesArray = new JSONArray(userDTO.getRoles());
				for (int count = 0; count < rolesArray.length() ; count++) {
					Role role = service.getRoleByName(rolesArray.getString(count));
					roleList.add(role);
				}
			} catch (JSONException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			
		}
		user.setRoles(roleList);
		service.saveUser(user);
		log.info("User:"+service.getUsername()+" added user with username: "+userDTO.getUsername()+" on "+Calendar.getInstance().getTime());
		return Collections.singletonMap("u", "Saved");
	}
	
	@RequestMapping( value = Views.EDIT_USER, method = RequestMethod.GET )
    public String editUser(Model model,@RequestParam( value = "id", required = false )
    Integer user_id ){
		User dbUser = service.getUser(user_id);
		UserDTO user = new UserDTO();
		user.setEmail(dbUser.getEmail());
		user.setFirstname(dbUser.getFirstname());
		user.setId(String.valueOf(dbUser.getId()));
		user.setLastname(dbUser.getLastname());
		user.setPassword(dbUser.getPassword());
		user.setPassword2(dbUser.getPassword());
		user.setSurname(dbUser.getSurname());
		user.setUsername(dbUser.getUsername());
		List<String> roles=new ArrayList<String>();
		for(int i=0;i<dbUser.getRoles().size();i++ ){
			roles.add(dbUser.getRoles().get(i).getRoleName());
		}
		model.addAttribute("userRoles",roles);
		model.addAttribute("roleList", service.getAllRoles());
		model.addAttribute("user", user);
		return Views.EDIT_USER;
	}
	
	@RequestMapping( value = Views.EDIT_USER, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> editUser(@RequestBody UserDTO userDTO ){
		if(userDTO.getRoles().isEmpty()||userDTO.getRoles()==null)
			return Collections.singletonMap("u", "Please enter the user roles");
		if(service.getUser(Integer.parseInt(userDTO.getId()))==null)
			return Collections.singletonMap("u", "User with that id does not exists");
		if(userDTO.getPassword()!=null && !userDTO.getPassword().isEmpty() && !userDTO.getPassword().equals(userDTO.getPassword2()))
			return Collections.singletonMap("u", "Passwords do not match");
		
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		User user = service.getUser(Integer.parseInt(userDTO.getId()));
		user.setEmail(userDTO.getEmail());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		if(userDTO.getPassword()!=null && !userDTO.getPassword().isEmpty()){
			user.setPassword(encoder.encodePassword(userDTO.getPassword(), null));
		}			
		user.setSurname(userDTO.getSurname());
		user.setUsername(userDTO.getUsername());
		List<Role> roleList = new ArrayList<Role>();
		if (!userDTO.getRoles().startsWith("[")) {
			Role role = service.getRoleByName(userDTO.getRoles());
			roleList.add(role);
		} else {
			JSONArray rolesArray=null;
			try {
				rolesArray = new JSONArray(userDTO.getRoles());
				for (int count = 0; count < rolesArray.length() ; count++) {
					Role role = service.getRoleByName(rolesArray.getString(count));
					roleList.add(role);
				}
			} catch (JSONException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			
		}
		user.setRoles(roleList);
		service.saveUser(user);
		log.info("User:"+service.getUsername()+" edited user with username: "+userDTO.getUsername()+" on "+Calendar.getInstance().getTime());
		return Collections.singletonMap("u", "Saved");
	}
	
	@RequestMapping( value = Views.DELETE_USER, method = RequestMethod.GET )
    public String deleteUser(Model model,@RequestParam( value = "id", required = false )
    Integer user_id ){
		User user = service.getUser(user_id);
		if(user.getEnabled()==1)
			user.setEnabled(0);
		else
			user.setEnabled(1);
		service.saveUser(user);	
		log.info("User:"+service.getUsername()+" enabled/disabled user with username: "+user.getUsername()+" on "+Calendar.getInstance().getTime());
		return Views.LIST_USER;
	}
	
	@RequestMapping( value = Views.DB_CONFIGURE, method = RequestMethod.GET )
    public String configDatabase(HttpServletRequest request, Model model ){
		DatabaseProperties dbProperties = new DatabaseProperties();
		try {
			Properties prop = new Properties();
			//load a properties file
			ServletContext servletContext = request.getSession().getServletContext();
			Resource resource = new ServletContextResource(servletContext,databaseProperties);
			prop.load(new FileInputStream(resource.getFile()));

            //get the property value and print it out
			String removeProtocol = prop.getProperty("jdbc.url").replace("jdbc:mysql://", ""); /* localhost:3306/ */
			String databaseUrl = removeProtocol.substring(0, removeProtocol.indexOf(":"));
			String databaseName = removeProtocol.substring(removeProtocol.indexOf("/"));
			dbProperties.setDatabaseUrl(databaseUrl);
			dbProperties.setDatabaseName(databaseName);
			dbProperties.setUsername(prop.getProperty("jdbc.username"));
			dbProperties.setPassword(prop.getProperty("jdbc.password"));
			model.addAttribute("dbProperties", dbProperties);

		}catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			log.error("\n "+e.getMessage()+"\n");
		}		
		return Views.DB_CONFIGURE;
	}
	
	@RequestMapping(value = Views.DB_CONFIGURE, method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> configServer(
		   HttpServletRequest request,
		   @RequestBody DatabaseProperties dbProperties) throws Exception {
    	
    	Set<ConstraintViolation<DatabaseProperties>> failures = validator.validate(dbProperties);
		if (!failures.isEmpty()) {
			return Collections.singletonMap("u", validationDBMessages(failures).toString());
		} else{
			try {
				Properties properties = new Properties();
				properties.setProperty("jdbc.url", "jdbc:mysql://"+dbProperties.getDatabaseUrl()+":3306/"+dbProperties.getDatabaseName());
				properties.setProperty("jdbc.username", dbProperties.getUsername());
				properties.setProperty("jdbc.password", dbProperties.getPassword());
				properties.setProperty("hibernate.hbm2ddl.auto", "update");
				properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
				properties.setProperty("hibernate.query.substitutions", "true 'T', false 'F'");
				properties.setProperty("hibernate.show_sql", "false");
				properties.setProperty("hibernate.c3p0.minPoolSize", "50");
				properties.setProperty("hibernate.c3p0.maxPoolSize", "1000");
				properties.setProperty("hibernate.c3p0.timeout", "300");
				properties.setProperty("hibernate.c3p0.max_statement", "700");
				properties.setProperty("hibernate.c3p0.testConnectionOnCheckout", "false");
				properties.setProperty("jdbc.driverClassName", "com.mysql.jdbc.Driver");
				
				//save properties to project root folder
				ServletContext servletContext = request.getSession().getServletContext();
				Resource resource = new ServletContextResource(servletContext,databaseProperties);
				properties.store(new FileOutputStream(resource.getFile()), null);
			} catch (IOException e) {
				log.error("\n "+e.getMessage()+"\n");
				return Collections.singletonMap("u", e.getMessage());
			}	        
	        return Collections.singletonMap("u", "Saved");
		}	
	} 

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
		        log.info("User:"+service.getUsername()+" added DHISv2 Server details: URL:"+server.getsUrl()+"; Username:"+server.getUsername()+"; Password:"+server.getPassword()+"; on "+Calendar.getInstance().getTime());
		        serverService.updateDhisServer(server);
	        }else{
	        	objServer.setUrl( url );
	        	objServer.setsUrl(url);
	        	objServer.setUsername( dhis2Server.getUsername() );
	        	objServer.setPassword( dhis2Server.getPassword() );
	        	log.info("User:"+service.getUsername()+" updated DHISv2 Server details: URL:"+objServer.getsUrl()+"; Username:"+objServer.getUsername()+"; Password:"+objServer.getPassword()+"; on "+Calendar.getInstance().getTime());
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
	private Map<String, String> validationDBMessages(
			Set<ConstraintViolation<DatabaseProperties>> failures) 
	{
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<DatabaseProperties> failure : failures) 
		{
			failureMessages.put(failure.getPropertyPath().toString(), 
								failure.getMessage());
		}
		return failureMessages;
	}
}
