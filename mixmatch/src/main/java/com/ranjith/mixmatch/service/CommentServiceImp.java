package com.ranjith.mixmatch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.entity.Comment;
import com.ranjith.mixmatch.entity.Post;
import com.ranjith.mixmatch.entity.UserProfile;
import com.ranjith.mixmatch.repository.CommentRepository;
import com.ranjith.mixmatch.repository.PostRepository;
import com.ranjith.mixmatch.repository.UserProfileRepository;
import com.ranjith.mixmatch.util.ResponseStatusEnum;

@Service
public class CommentServiceImp implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;

	@Override
	public ResponseObject insertComment(Comment comment,IdObject postId,IdObject userProfileId) {
        Optional<Post> optPost = postRepository.findById(postId.getId());
        Optional<UserProfile> optUserProfile = userProfileRepository.findById(userProfileId.getId());
        if (optPost.isEmpty()) {
        	return ResponseObject.builder().message("cannot find target post id: " + postId).status(ResponseStatusEnum.FAIL).payload(null).build();
        }else {
        	Post post = optPost.get();
        	List<Comment> commentList = post.getComment();
        	if(commentList==null) commentList = new ArrayList<>();
        	commentList.add(comment);
        	comment.setPost(post);
        	comment.setUserProfile(optUserProfile.get());
        	System.out.println(comment);
        	commentRepository.save(comment);
        	return ResponseObject.builder().message("Added comment!").status(ResponseStatusEnum.SUCCESS).payload(comment).build();
        }
	}

	@Override
	public ResponseObject getCommentByPost(IdObject id) {
        Optional<Post> optTargetPost = postRepository.findById(id.getId());
        if(optTargetPost.isEmpty()) {
        	return ResponseObject.builder().message("cannot find the post").status(ResponseStatusEnum.FAIL).payload(null).build();
        }else {
        	Post post = optTargetPost.get();
        	List<Comment> commentList = post.getComment();
        	if(commentList.size() > 0) {
        		return ResponseObject.builder().message("Success").status(ResponseStatusEnum.SUCCESS).payload(commentList).build();
        	}else {
        		return ResponseObject.builder().message("Post id " + id.getId() + " does not have any comment").status(ResponseStatusEnum.FAIL).payload(null).build();        	}
        }

	}

}
