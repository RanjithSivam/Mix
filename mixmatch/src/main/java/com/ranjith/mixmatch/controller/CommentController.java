package com.ranjith.mixmatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.mixmatch.dto.CommentPostObject;
import com.ranjith.mixmatch.dto.IdObject;
import com.ranjith.mixmatch.dto.ResponseObject;
import com.ranjith.mixmatch.entity.Comment;
import com.ranjith.mixmatch.service.CommentService;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
    @PostMapping("/insert")
	public ResponseEntity<ResponseObject> insertComment(@RequestBody CommentPostObject postedComment) {
    	
        Comment inputComment = postedComment.getComment();
        IdObject inputPostId = postedComment.getId();
        IdObject inputUserProfileId = postedComment.getUserProfileId();
        return new ResponseEntity<ResponseObject>(commentService.insertComment(inputComment, inputPostId,inputUserProfileId), HttpStatus.OK);
    }
    
    @PostMapping("/getcomments") 
    public ResponseEntity<ResponseObject> getComments(@RequestBody IdObject inputPostId) {
        return new ResponseEntity<ResponseObject>(commentService.getCommentByPost(inputPostId), HttpStatus.OK);
    }
}
