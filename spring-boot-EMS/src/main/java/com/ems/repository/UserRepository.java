package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);
	
}
