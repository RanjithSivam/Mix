package com.ranjith.mixmatch.service;

import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.LikeShareObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.entity.Post;

public interface PostService {
	public ResponseObject insertPost(Post post);
	public ResponseObject findPostByUserId(IdObject id);
	public ResponseObject findPostByFollowing(IdObject id);
//	public ResponseObject updatePostByComment(Post post);
	public ResponseObject updatePostByLove(LikeShareObject ids);
	public ResponseObject updatePostByShare(LikeShareObject ids);
	
}
