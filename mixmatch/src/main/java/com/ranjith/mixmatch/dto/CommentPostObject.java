package com.ranjith.mixmatch.dto;

import com.ranjith.mixmatch.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CommentPostObject {
	private Comment comment;
	private IdObject id;
	private IdObject userProfileId;
}
