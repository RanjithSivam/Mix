package com.ranjith.mixmatch.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ranjith.mixmatch.dto.FollowFollowerObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.dto.SignupDto;
import com.ranjith.mixmatch.entity.User;
import com.ranjith.mixmatch.entity.UserProfile;
import com.ranjith.mixmatch.repository.UserProfileRepository;
import com.ranjith.mixmatch.repository.UserRepository;
import com.ranjith.mixmatch.util.ResponseStatusEnum;
import com.ranjith.mixmatch.util.RoleEnum;

@Service
public class UserServiceImp implements UserService {
	
	@Autowired
    private UserProfileRepository userProfileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bCryptEncoder;
	
//    private RestTemplate restTemplate;
//	
//	public UserServiceImp(RestTemplateBuilder restTemplateBuilder) {
//		restTemplate = restTemplateBuilder.build();
//	}
	
	@Override
	public ResponseObject findAll() {
		return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("success").payload(userProfileRepository.findAll()).build();
	}

	@Override
	public ResponseObject findById(int id) {
        Optional<UserProfile> optUser = userProfileRepository.findById(id);
        if(optUser.isEmpty()) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).message("User id: " + id + " not existed.").isError(true).payload(null).build();
        }
  
        return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("User has been successfully found!").payload(optUser.get()).build();
	}

	@Override
	public ResponseObject findFollowing(int id) {
		Optional<UserProfile> optUser = userProfileRepository.findById(id);
		if(optUser.isEmpty()) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).message("User id: " + id + " not existed").isError(true).payload(null).build();
        }
		
        List<Integer> followingIds = optUser.get().getFollowing();
        List<UserProfile> followingAccounts = new ArrayList<>();
        
        if(followingIds.size() ==0) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).message(optUser.get().getFirstName()+" "+optUser.get().getLastName()+" does not follow anyone").isError(true).payload(null).build();
        }
        
        for (Integer followingId : followingIds) {
            Optional<UserProfile> optFollowingUser = userProfileRepository.findById(followingId);
            if (optFollowingUser.isPresent()) {
            	UserProfile followingUser = optFollowingUser.get();
                followingAccounts.add(followingUser);
            }
        }
        
        return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("Success").payload(followingAccounts).build();
	}

	@Override
	public ResponseObject findFollower(int id) {
		Optional<UserProfile> optUser = userProfileRepository.findById(id);
		
		if(optUser.isEmpty()) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).isError(true).message("User id: " + id + " not existed").payload(null).build();
        }
		
		List<UserProfile> followerAccounts = new ArrayList<>();
		List<Integer> followerIds = optUser.get().getFollower();
        
        if(followerIds.size() ==0) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).message(optUser.get().getFirstName()+" "+optUser.get().getLastName()+" does not have any followers").isError(true).payload(null).build();
        }
        
        for (Integer followerId : followerIds) {
            Optional<UserProfile> optFollowingUser = userProfileRepository.findById(followerId);
            if (optFollowingUser.isPresent()) {
            	UserProfile followingUser = optFollowingUser.get();
            	followerAccounts.add(followingUser);
            }
        }
        
        return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("Success").payload(followerAccounts).build();
		
	}

	@Override
	public ResponseObject saveUser(SignupDto signupDto) {
		String url = "https://ui-avatars.com/api/?size=128&name="+signupDto.getEmail();
        Optional<User> optUser = userRepository.findByEmail(signupDto.getEmail());
        
        if(optUser.isPresent()) { 
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).isError(true).message("Email address " + signupDto.getEmail() + " existed").payload(null).build();
        }
        User user = User.builder().email(signupDto.getEmail()).password(bCryptEncoder.encode(signupDto.getPassword())).role(RoleEnum.USER).build();
        UserProfile userProfile = userProfileRepository.save(UserProfile.builder().firstName(signupDto.getFirstName()).lastName(signupDto.getLastName()).email(user.getEmail()).user(user).imageUrl(url).build());
        List<Integer> following = userProfile.getFollowing();
        
        if(following==null) {
        	following = new ArrayList<>();
        }
        
        following.add(userProfile.getId());
        this.update(userProfile);
        
        return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("Success").payload(userProfile).build();
	}

	@Override
	public ResponseObject update(UserProfile userProfile) {
		Optional<UserProfile> optUser = userProfileRepository.findById(userProfile.getId());
		if(optUser.isEmpty())
			return ResponseObject.builder().status(ResponseStatusEnum.FAIL).message("User id not existed").isError(true).payload(null).build();
		userProfile.setCreatedAt(optUser.get().getCreatedAt());
		userProfile.setEmail(optUser.get().getEmail());
		userProfile.setUser(optUser.get().getUser());
		userProfileRepository.save(userProfile);
		return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("Success").payload(userProfile).build();
	}

	@Override
	public ResponseObject followUser(FollowFollowerObject ids) {
		Optional<UserProfile> optFollowedUser = userProfileRepository.findById(ids.getFollow());
        Optional<UserProfile> optFollower = userProfileRepository.findById(ids.getFollower());
        
        if(optFollowedUser.isEmpty() || optFollower.isEmpty()) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).isError(true).message("User id not existed").payload(null).build();
        }
		
        UserProfile followedUser = optFollowedUser.get();
        UserProfile follower = optFollower.get();
        
        List<Integer> followerList = followedUser.getFollower();
        if (followerList == null) {
            followerList = new ArrayList<>();
        }
        followerList.add(follower.getId());
        followedUser.setFollower(followerList);
        
        List<Integer> followingList = follower.getFollowing();
        if (followingList == null) {
            followingList = new ArrayList<>();
        }
        followingList.add(followedUser.getId());
        follower.setFollowing(followingList);
        
        userProfileRepository.save(followedUser);
        userProfileRepository.save(follower);
        
        return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("User id " + follower.getId() + " successfully followed user id " + followedUser.getId()).payload(follower).build();
	}

	@Override
	public ResponseObject unFollowUser(FollowFollowerObject ids) {
		Optional<UserProfile> optFollowedUser = userProfileRepository.findById(ids.getFollow());
        Optional<UserProfile> optFollower = userProfileRepository.findById(ids.getFollower());
        
        if(optFollowedUser.isEmpty() || optFollower.isEmpty()) {
        	return ResponseObject.builder().status(ResponseStatusEnum.FAIL).isError(true).message("User id not existed").payload(null).build();
        }
		
        UserProfile followedUser = optFollowedUser.get();
        UserProfile follower = optFollower.get();
        
        List<Integer> followerList = followedUser.getFollower();
        if (followerList == null) {
            followerList = new ArrayList<>();
        }
        followerList.remove(follower.getId());
        followedUser.setFollower(followerList);
        
        List<Integer> followingList = follower.getFollowing();
        if (followingList == null) {
            followingList = new ArrayList<>();
        }
        followingList.remove(followedUser.getId());
        follower.setFollowing(followingList);
        
        userProfileRepository.save(followedUser);
        userProfileRepository.save(follower);
   
        return ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("User id " + follower.getId() + " successfully unfollowed user id " + followedUser.getId()).payload(follower).build();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find user with email: " + email);
        } else {
            User foundUser = optUser.get();
            RoleEnum role = foundUser.getRole();
            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(role.toString()));
            org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(), ga);
            return springUser;
        }
	}

}
