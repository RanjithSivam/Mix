package com.ranjith.mixmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ranjith.mixmatch.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	@Query("select post from Post as post where post.userProfile.id = :userProfileId")
	Optional<List<Post>> findByUserProfileId(Integer userProfileId);
	
	@Query("select post from Post as post where post.userProfile.id = :userProfileId order by post.createdAt desc")
	Optional<List<Post>> findByUserProfileIdOrderByCreatedAtDesc(Integer userProfileId);
}
