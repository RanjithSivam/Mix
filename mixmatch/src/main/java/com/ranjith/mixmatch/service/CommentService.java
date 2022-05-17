package com.ranjith.mixmatch.service;

import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.entity.Comment;

public interface CommentService {
	public ResponseObject insertComment(Comment comment,IdObject id,IdObject userProfileId);
	public ResponseObject getCommentByPost(IdObject id);
}
