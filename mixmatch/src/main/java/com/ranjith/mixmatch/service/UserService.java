package com.ranjith.mixmatch.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ranjith.mixmatch.dto.FollowFollowerObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.dto.SignupDto;
import com.ranjith.mixmatch.entity.User;
import com.ranjith.mixmatch.entity.UserProfile;

public interface UserService extends UserDetailsService {
	public ResponseObject findAll();
	public ResponseObject findById(int id);
	public ResponseObject findFollowing(int id);
	public ResponseObject findFollower(int id);
	public ResponseObject saveUser(SignupDto signupDto);
	public ResponseObject update(UserProfile userProfile);
	public ResponseObject followUser(FollowFollowerObject ids);
	public ResponseObject unFollowUser(FollowFollowerObject ids);
}
