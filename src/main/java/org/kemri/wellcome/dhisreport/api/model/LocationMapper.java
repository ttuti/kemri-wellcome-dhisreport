package org.kemri.wellcome.dhisreport.api.model;

import java.util.ArrayList;
import java.util.List;

import org.kemri.wellcome.dhisreport.api.dto.LocationDTO;
import org.springframework.data.domain.Page;

public class LocationMapper {
	
	public static LocationDTO map(Location location) 
	{
		LocationDTO locationDTO = new LocationDTO();	
		locationDTO.setId(String.valueOf(location.getId()));
		locationDTO.setCode(location.getCode());
		locationDTO.setName(location.getName());
		return locationDTO;
	}

	public static List<LocationDTO> map(Page<Location> locationPage) {
		List<LocationDTO> locationList = new ArrayList<LocationDTO>();
		for (Location location: locationPage) {
			locationList.add(map(location));
		}
		return locationList;
	}

}
