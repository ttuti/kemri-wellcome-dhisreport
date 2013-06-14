package org.kemri.wellcome.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hisp.dhis.dxf2.importsummary.ImportStatus;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.dto.ImportSummaryDTO;
import org.kemri.wellcome.dhisreport.api.model.ImportSummaryMapper;
import org.kemri.wellcome.dhisreport.api.model.JqGridFilter;
import org.kemri.wellcome.dhisreport.api.model.JqGridObjectMapper;
import org.kemri.wellcome.dhisreport.api.model.JqgridResponse;
import org.kemri.wellcome.dhisreport.api.utils.DateUtils;
import org.kemri.wellcome.dhisreport.repositories.ImportSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Timothy Tuti
 * @version 1.0
 *
 */

@Controller
public class IndexController {
	
	protected final Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	private ImportSummaryRepository importSummaryRepository;
	
	@RequestMapping(Views.INDEX)
	public String index(Model model) {
		return Views.INDEX;
	}
	@RequestMapping(Views.HOME)
	public String home(Model model) {
		return Views.HOME;
	}
	
	@RequestMapping(value = Views.SUMMARY, method = RequestMethod.GET)
	public String summaries() {
		return Views.SUMMARY;
	}
	
	@RequestMapping(value=Views.IMPORT_SUMMARY,produces= Views.MEDIA_TYPE_JSON)
	public @ResponseBody JqgridResponse<ImportSummaryDTO> records(
			@RequestParam("_search") Boolean search,
			@RequestParam(value="filters", required=false) String filters,		     
			@RequestParam(value="page", required=false, defaultValue="1") Integer page,														 
			@RequestParam(value="rows", required=false, defaultValue="10") Integer rows,
			@RequestParam(value="sidx", required=false) String sidx,
		    @RequestParam(value="sord", required=false) String sord
			) {
		
		Sort sort = new Sort(new Order(Direction.DESC, "id"));
		Pageable pageRequest = new PageRequest(page-1, rows, sort);
		
		if(search == true) {
			return getFilteredSummaries(filters, pageRequest);
		}
		
		Page<ImportSummary> summaries = importSummaryRepository.findAll(pageRequest);
		List<ImportSummaryDTO> summaryDTOList = ImportSummaryMapper.map(summaries);

		JqgridResponse<ImportSummaryDTO> response = new JqgridResponse<ImportSummaryDTO>();
		response.setRows(summaryDTOList);
		response.setRecords(Long.valueOf(summaries.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(summaries.getTotalPages()).toString());
		response.setPage(Integer.valueOf(summaries.getNumber()+1).toString());

		return response;
	}
	
	public JqgridResponse<ImportSummaryDTO> getFilteredSummaries(String filters,
			Pageable pageRequest) {
		Date qDate = null;
		String qReportName = null;
		String qStatus = null;

		JqGridFilter jqgridFilter = JqGridObjectMapper.map(filters);
		for (JqGridFilter.Rule rule : jqgridFilter.getRules()) {
			if (rule.getField().equals("report"))
				qReportName =  rule.getData();
			else if (rule.getField().equals("status"))
				qStatus = rule.getData();
			else if (rule.getField().equals("date"))
				qDate = DateUtils.stringToDate(rule.getData());
		}

		Page<ImportSummary> summaries = null;
		if (qDate != null){
			summaries = importSummaryRepository.findByReportDateOrderByIdDesc(qDate, pageRequest);
		}			
		if (qReportName != null){
			summaries = importSummaryRepository.findByReportNameLikeOrderByIdDesc("%"+qReportName+"%",pageRequest);
		}
		if(qStatus != null){
			if (qStatus.equalsIgnoreCase("SUCCESS"))
				summaries = importSummaryRepository.findByStatusOrderByIdDesc(ImportStatus.SUCCESS, pageRequest);
			if (qStatus.equalsIgnoreCase("ERROR"))
				summaries = importSummaryRepository.findByStatusOrderByIdDesc(ImportStatus.ERROR, pageRequest);
		}
		if(qDate !=null && qReportName !=null){
			summaries = importSummaryRepository.findByReportNameLikeAndReportDateOrderByIdDesc("%"+qReportName+"%",qDate, pageRequest);
		}
		if(qStatus !=null && qReportName !=null){
			if (qStatus.equalsIgnoreCase("SUCCESS"))
				summaries = importSummaryRepository.findByReportNameLikeAndStatusOrderByIdDesc("%"+qReportName+"%",ImportStatus.SUCCESS, pageRequest);
			if (qStatus.equalsIgnoreCase("ERROR"))
				summaries = importSummaryRepository.findByReportNameLikeAndStatusOrderByIdDesc("%"+qReportName+"%",ImportStatus.ERROR, pageRequest);
		}
		if(qDate!=null && qStatus !=null){
			if (qStatus.equalsIgnoreCase("SUCCESS"))
				summaries = importSummaryRepository.findByStatusAndReportDateOrderByIdDesc(ImportStatus.SUCCESS,qDate, pageRequest);
			if (qStatus.equalsIgnoreCase("ERROR"))
				summaries = importSummaryRepository.findByStatusAndReportDateOrderByIdDesc(ImportStatus.ERROR,qDate, pageRequest);
		}
		if(qReportName !=null && qStatus !=null && qDate !=null){
			if (qStatus.equalsIgnoreCase("SUCCESS"))
				summaries = importSummaryRepository.findByReportNameLikeAndStatusAndReportDateOrderByIdDesc("%"+qReportName+"%",ImportStatus.SUCCESS,qDate, pageRequest);
			if (qStatus.equalsIgnoreCase("ERROR"))
				summaries = importSummaryRepository.findByReportNameLikeAndStatusAndReportDateOrderByIdDesc("%"+qReportName+"%",ImportStatus.ERROR,qDate, pageRequest);
		}
		

		List<ImportSummaryDTO> summaryDTOList = ImportSummaryMapper.map(summaries);
		JqgridResponse<ImportSummaryDTO> response = new JqgridResponse<ImportSummaryDTO>();
		response.setRows(summaryDTOList);
		response.setRecords(Long.valueOf(summaries.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(summaries.getTotalPages()).toString());
		response.setPage(Integer.valueOf(summaries.getNumber() + 1).toString());
		return response;
	}
}
