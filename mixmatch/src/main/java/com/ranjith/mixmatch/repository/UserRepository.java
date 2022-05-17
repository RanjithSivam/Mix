package com.ranjith.mixmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ranjith.mixmatch.entity.User;
import com.ranjith.mixmatch.util.RoleEnum;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
}
