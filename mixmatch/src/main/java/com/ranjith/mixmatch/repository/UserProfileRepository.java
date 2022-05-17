package com.ranjith.mixmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ranjith.mixmatch.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{
	
	Optional<UserProfile> findById(Integer id);

	Optional<UserProfile> findByEmail(String email);
}
