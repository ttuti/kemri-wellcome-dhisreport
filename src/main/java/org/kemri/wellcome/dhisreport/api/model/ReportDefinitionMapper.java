package org.kemri.wellcome.dhisreport.api.model;

import java.util.ArrayList;
import java.util.List;

import org.kemri.wellcome.dhisreport.api.dto.ReportDefinitionDTO;
import org.springframework.data.domain.Page;

public class ReportDefinitionMapper {
	
	public static ReportDefinitionDTO map(ReportDefinition reportDefinition) 
	{
		ReportDefinitionDTO reportDefinitionDTO = new ReportDefinitionDTO();	
		reportDefinitionDTO.setId(String.valueOf(reportDefinition.getId()));
		reportDefinitionDTO.setCode(reportDefinition.getCode());
		reportDefinitionDTO.setName(reportDefinition.getName());
		reportDefinitionDTO.setUid(reportDefinition.getUid());
		return reportDefinitionDTO;
	}

	public static List<ReportDefinitionDTO> map(Page<ReportDefinition> reportDefinitionPage) {
		List<ReportDefinitionDTO> reportDefinitionList = new ArrayList<ReportDefinitionDTO>();
		for (ReportDefinition reportDefinition: reportDefinitionPage) {
			reportDefinitionList.add(map(reportDefinition));
		}
		return reportDefinitionList;
	}

}
