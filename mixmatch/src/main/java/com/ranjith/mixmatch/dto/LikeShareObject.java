package com.ranjith.mixmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LikeShareObject {
	Integer postId;
	Integer likedUserId;
}
