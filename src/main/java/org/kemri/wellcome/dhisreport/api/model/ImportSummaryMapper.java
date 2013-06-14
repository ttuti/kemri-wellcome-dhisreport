package org.kemri.wellcome.dhisreport.api.model;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.dto.ImportSummaryDTO;
import org.kemri.wellcome.dhisreport.api.utils.DateUtils;
import org.springframework.data.domain.Page;

public class ImportSummaryMapper {
	
	public static ImportSummaryDTO map(ImportSummary importSummary) 
	{
		ImportSummaryDTO importSummaryDTO = new ImportSummaryDTO();	
		importSummaryDTO.setId(String.valueOf(importSummary.getId()));
		importSummaryDTO.setStatus(importSummary.getStatus().name());
		importSummaryDTO.setDate(DateUtils.dateToString(importSummary.getReportDate()));
		importSummaryDTO.setComplete(importSummary.getDataSetComplete());
		importSummaryDTO.setConflicts(String.valueOf(importSummary.getConflicts().size()));
		importSummaryDTO.setCount(importSummary.getDataValueCount().toString());
		importSummaryDTO.setReport(importSummary.getReportName());
		return importSummaryDTO;
	}

	public static List<ImportSummaryDTO> map(Page<ImportSummary> importPage) {
		List<ImportSummaryDTO> importSummaryList = new ArrayList<ImportSummaryDTO>();
		for (ImportSummary importSummary: importPage) {
			importSummaryList.add(map(importSummary));
		}
		return importSummaryList;
	}

}
