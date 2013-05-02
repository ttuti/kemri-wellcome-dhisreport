package org.kemri.wellcome.dhisreport.api.model;

import org.codehaus.jackson.map.ObjectMapper;

/**
* Maps a jQgrid JSON query to a {@link JqgridFilter} instance
*/
public class JqGridObjectMapper {

	public static JqGridFilter map(String jsonString) {

		if (jsonString != null) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				return mapper.readValue(jsonString, JqGridFilter.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return null;
	}
}