	package com.ranjith.mixmatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.LikeShareObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.entity.Post;
import com.ranjith.mixmatch.service.PostService;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/add")
    public ResponseEntity<ResponseObject> insertPost(@RequestBody Post inputPost) {
        return new ResponseEntity<ResponseObject>(postService.insertPost(inputPost), HttpStatus.OK);
    }
	
	@PostMapping("/myposts")
    public ResponseEntity<ResponseObject> findPostByUserId(@RequestBody IdObject inputUserId) {
        return new ResponseEntity<ResponseObject>(postService.findPostByUserId(inputUserId), HttpStatus.OK);
    }
	
	@PostMapping("/followingposts")
    public ResponseEntity<ResponseObject> findPostByFollowing(@RequestBody IdObject inputUserId) {
        return new ResponseEntity<ResponseObject>(postService.findPostByFollowing(inputUserId), HttpStatus.OK);
    }
	
	@PostMapping("/lovepost")
    public ResponseEntity<ResponseObject> lovePost(@RequestBody LikeShareObject ids) {
        return new ResponseEntity<ResponseObject>(postService.updatePostByLove(ids), HttpStatus.OK);
    }

    @PostMapping("/sharepost")
    public ResponseEntity<ResponseObject> sharePost(@RequestBody LikeShareObject ids) {
        return new ResponseEntity<ResponseObject>(postService.updatePostByShare(ids), HttpStatus.OK);
    }

}
