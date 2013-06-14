package org.kemri.wellcome.dhisreport.repositories;

import java.util.Date;

import org.hisp.dhis.dxf2.importsummary.ImportStatus;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportSummaryRepository extends
		JpaRepository<ImportSummary, Integer> {
	
	Page<ImportSummary> findAll(Pageable pageable);
	
	Page<ImportSummary> findByReportNameLikeOrderByIdDesc(String report, Pageable pageable);
	
	Page<ImportSummary> findByStatusOrderByIdDesc(ImportStatus status, Pageable pageable);
	
	Page<ImportSummary> findByReportDateOrderByIdDesc(Date date, Pageable pageable);
	
	Page<ImportSummary> findByReportNameLikeAndStatusOrderByIdDesc(String report,ImportStatus status, Pageable pageable);
	
	Page<ImportSummary> findByStatusAndReportDateOrderByIdDesc(ImportStatus status,Date date, Pageable pageable);
	
	Page<ImportSummary> findByReportNameLikeAndReportDateOrderByIdDesc(String report,Date date, Pageable pageable);
	
	Page<ImportSummary> findByReportNameLikeAndStatusAndReportDateOrderByIdDesc(String report,ImportStatus status,Date date, Pageable pageable);
	

}
