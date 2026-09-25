package com.edumerge.support.repository;
import com.edumerge.support.entity.User;
import com.edumerge.support.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


	public interface UserRepository extends JpaRepository<User, Long> {

	    Optional<User> findByEmail(String email);

	    List<User> findByRole(Role role);

	    List<User> findByRoleAndActiveTrue(Role role);

	    boolean existsByEmail(String email);
	}


