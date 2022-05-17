package com.ranjith.mixmatch.dto;

import com.ranjith.mixmatch.entity.UserProfile;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorizedObject {
	
	private UserProfile userProfile;
	private String jwt;
}
