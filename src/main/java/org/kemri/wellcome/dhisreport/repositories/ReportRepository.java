package org.kemri.wellcome.dhisreport.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinition;

public interface ReportRepository extends JpaRepository<ReportDefinition,Integer> {
	
	Page<ReportDefinition> findAll(Pageable pageable);	
	Page<ReportDefinition> findByNameLike(String name,Pageable pageable);
	Page<ReportDefinition> findByCodeLike(String code,Pageable pageable);
	Page<ReportDefinition> findByNameLikeAndCodeLike(String name,String code,Pageable pageable);
}
