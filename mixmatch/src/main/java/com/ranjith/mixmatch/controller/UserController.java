package com.ranjith.mixmatch.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.mixmatch.dto.AuthorizedObject;
import com.ranjith.mixmatch.dto.FollowFollowerObject;
import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.dto.SignupDto;
import com.ranjith.mixmatch.entity.User;
import com.ranjith.mixmatch.entity.UserProfile;
import com.ranjith.mixmatch.repository.UserRepository;
import com.ranjith.mixmatch.service.UserService;
import com.ranjith.mixmatch.util.JWTUtil;
import com.ranjith.mixmatch.util.ResponseStatusEnum;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/hello")
    public String hello() {
    	return "hello";
    }
    
    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> saveUser(@RequestBody SignupDto inputUser) {
        return new ResponseEntity<ResponseObject>(userService.saveUser(inputUser), HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> userSignIn(@RequestBody User inputUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputUser.getEmail(), inputUser.getPassword()));
            String token = jwtUtil.generateToken(inputUser.getEmail());
            
            Optional<User> optUser = userRepository.findByEmail(inputUser.getEmail());
            UserProfile userProfile = optUser.get().getUserProfile();
        
            return new ResponseEntity<ResponseObject>(ResponseObject.builder().status(ResponseStatusEnum.SUCCESS).message("authenticated").payload(AuthorizedObject.builder().userProfile(userProfile).jwt(token).build()).build(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResponseObject>(ResponseObject.builder().status(ResponseStatusEnum.FAIL).message(ex.getMessage()).payload(null).build(), HttpStatus.OK);
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<ResponseObject> findAllUsers() {
        return new ResponseEntity<ResponseObject>(userService.findAll(), HttpStatus.OK);
    }
    
    @PostMapping("/profile")
    public ResponseEntity<ResponseObject> findById(@RequestBody IdObject inputId) {
        return new ResponseEntity<ResponseObject>(userService.findById(inputId.getId()), HttpStatus.OK);
    }
    
    @PostMapping("/follow")
    public ResponseEntity<ResponseObject> followUser(@RequestBody FollowFollowerObject doubleId) {
        return new ResponseEntity<ResponseObject>(userService.followUser(doubleId), HttpStatus.OK);
    }
    
    @PostMapping("/unfollow")
    public ResponseEntity<ResponseObject> unfollowUser(@RequestBody FollowFollowerObject doubleId) {
        return new ResponseEntity<ResponseObject>(userService.unFollowUser(doubleId), HttpStatus.OK);
    }
    
    @PostMapping("/getfollowing")
    public ResponseEntity<ResponseObject> findFollowing(@RequestBody IdObject inputId) {
        return new ResponseEntity<ResponseObject>(userService.findFollowing(inputId.getId()), HttpStatus.OK);
    }
    
    @PostMapping("/getfollower")
    public ResponseEntity<ResponseObject> findFollower(@RequestBody IdObject inputId) {
        return new ResponseEntity<ResponseObject>(userService.findFollower(inputId.getId()), HttpStatus.OK);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseObject> update(@RequestBody UserProfile inputUser) {
        return new ResponseEntity<ResponseObject>(userService.update(inputUser), HttpStatus.OK);
    }
}
