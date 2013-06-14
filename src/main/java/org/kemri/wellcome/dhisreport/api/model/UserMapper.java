package org.kemri.wellcome.dhisreport.api.model;

import java.util.ArrayList;
import java.util.List;

import org.kemri.wellcome.dhisreport.api.dto.UserDTO;
import org.springframework.data.domain.Page;

public class UserMapper {
	
	public static UserDTO map(User user) 
	{
		UserDTO userDTO = new UserDTO();	
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstname(user.getFirstname());
		userDTO.setLastname(user.getLastname());
		userDTO.setSurname(user.getSurname());
		userDTO.setUsername(user.getUsername());
		userDTO.setId(String.valueOf(user.getId()));
		userDTO.setStatus(String.valueOf(user.getEnabled()));
		return userDTO;
	}

	public static List<UserDTO> map(Page<User> users) {
		
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (User user: users) {
			userDTOList.add(map(user));
		}
		return userDTOList;
	}

}

