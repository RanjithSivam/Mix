package com.ranjith.mixmatch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.LikeShareObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.entity.Post;
import com.ranjith.mixmatch.entity.UserProfile;
import com.ranjith.mixmatch.repository.PostRepository;
import com.ranjith.mixmatch.repository.UserProfileRepository;
import com.ranjith.mixmatch.util.ResponseStatusEnum;

@Service
public class PostServiceImp implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;

	@Override
	public ResponseObject insertPost(Post post) {
		return ResponseObject.builder().message("Post created successfully!").status(ResponseStatusEnum.SUCCESS)
		.payload(postRepository.save(post)).build();
	}

	@Override
	public ResponseObject findPostByUserId(IdObject id) {
		Optional<List<Post>> userPostOpt = postRepository.findByUserProfileIdOrderByCreatedAtDesc(id.getId());
		if(userPostOpt.isEmpty()) {
			return ResponseObject.builder().message("Cannot find any post from userId: "+id.getId()).status(ResponseStatusEnum.FAIL).payload(null).build();
		}else {
			List<Post> userPost = userPostOpt.get();
			return ResponseObject.builder().message("Success").status(ResponseStatusEnum.SUCCESS).payload(userPost).build();
		}
	}

	@Override
	public ResponseObject findPostByFollowing(IdObject id) {
		Optional<UserProfile> userProfileOpt = userProfileRepository.findById(id.getId());
		if(userProfileOpt.isEmpty()) {
			return ResponseObject.builder().message("Cannot find any post from userId: "+id.getId()).status(ResponseStatusEnum.FAIL).payload(null).build();
		}else {
			List<Post> allFollowingPost = new ArrayList<>();
			Optional<List<Post>> followingPostsOpt = postRepository.findByUserProfileId(id.getId());
            if (followingPostsOpt.isPresent()) {
                List<Post> followingPosts = followingPostsOpt.get();
                if (followingPosts != null) {
                	allFollowingPost.addAll(followingPosts);
                }
            }
			UserProfile user = userProfileOpt.get();
			if(user.getFollowing()!=null) {
				for(Integer followId:user.getFollowing()) {
	                    followingPostsOpt = postRepository.findByUserProfileId(followId);
	                    if (followingPostsOpt.isPresent()) {
	                        List<Post> followingPosts = followingPostsOpt.get();
	                        if (followingPosts != null) {
	                        	allFollowingPost.addAll(followingPosts);
	                        }
	                    }
				}
				
				
				Collections.sort(allFollowingPost,(o1,o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
				return ResponseObject.builder().message("All posts").status(ResponseStatusEnum.SUCCESS).payload(allFollowingPost).build();
			}else {
				return ResponseObject.builder().message("user id: " + id.getId() + " has empty following list").status(ResponseStatusEnum.FAIL).payload(null).build();
			}
		}
	}

	@Override
	public ResponseObject updatePostByLove(LikeShareObject ids) {
		Optional<Post> postOpt = postRepository.findById(ids.getPostId());
		if(postOpt.isEmpty()) {
			return ResponseObject.builder().message("cannot find post id: "+ids.getPostId()).status(ResponseStatusEnum.INVALID).payload(null).build();
		}else {
			Post post = postOpt.get();
			List<Integer> love = post.getLove();
			if(love == null) love = new ArrayList<>();
			
			if(!love.contains(ids.getLikedUserId())) love.add(ids.getLikedUserId());
			else love.remove(ids.getLikedUserId());
			
			post.setLove(love);
			postRepository.save(post);
			return ResponseObject.builder().message("update love to the target post id: "+ids.getPostId()).status(ResponseStatusEnum.SUCCESS).payload(post).build();
		}
	}

	@Override
	public ResponseObject updatePostByShare(LikeShareObject ids) {
		Optional<Post> postOpt = postRepository.findById(ids.getPostId());
		if(postOpt.isEmpty()) {
			return ResponseObject.builder().message("cannot find post id: "+ids.getPostId()).status(ResponseStatusEnum.INVALID).payload(null).build();
		}else {
			Post post = postOpt.get();
			List<Integer> share = post.getShare();
			if(share == null) share = new ArrayList<>();
			
			share.add(ids.getLikedUserId());
			post.setShare(share);
			postRepository.save(post);
			
			post = new Post();
			post.setId(null);
			post.setLove(new ArrayList<>());
			post.setShare(new ArrayList<>());
			post.setUserProfile(userProfileRepository.getById(ids.getLikedUserId()));
			post.setComment(new ArrayList<>());
			post.setContent("Shared a post: "+postOpt.get().getContent());
			postRepository.save(post);
			
			return ResponseObject.builder().message("update share to the target post id: "+ids.getPostId()).status(ResponseStatusEnum.SUCCESS).payload(post).build();
		}
	}

}
