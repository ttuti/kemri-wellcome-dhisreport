package org.kemri.wellcome.dhisreport.repositories;

import org.kemri.wellcome.dhisreport.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Page<User> findAll(Pageable pageable);
	
	Page<User> findByEmail(String email,Pageable pageable);
	
	Page<User> findByUsername(String username,Pageable pageable);
	
	Page<User> findByEnabled(Integer enabled,Pageable pageable);

}
