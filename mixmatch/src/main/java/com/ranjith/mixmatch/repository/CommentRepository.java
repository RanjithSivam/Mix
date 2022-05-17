package com.ranjith.mixmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ranjith.mixmatch.entity.Comment;
import com.ranjith.mixmatch.entity.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("select comment from Comment as comment where comment.post.id = :postId")
	List<Comment> findByPostId(Integer postId);
}
