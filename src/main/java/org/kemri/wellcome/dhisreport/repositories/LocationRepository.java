package org.kemri.wellcome.dhisreport.repositories;

import org.kemri.wellcome.dhisreport.api.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	Page<Location> findAll(Pageable pageable);
	Page<Location> findByNameLike(String name,Pageable pageable);
	Page<Location> findByCodeLike(String code,Pageable pageable);
	Page<Location> findByNameLikeAndCodeLike(String name,String code,Pageable pageable);

}
