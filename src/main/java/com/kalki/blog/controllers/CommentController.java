package com.kalki.blog.controllers;

import com.kalki.blog.config.MessageConstants;
import com.kalki.blog.payloads.ApiResponseWrapper;
import com.kalki.blog.payloads.CommentDto;
import com.kalki.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<ApiResponseWrapper<CommentDto>> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId) {
        CommentDto created = commentService.createComment(comment, postId);
        return new ResponseEntity<>(ApiResponseWrapper.success(MessageConstants.COMMENT_CREATED, created), HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.COMMENT_DELETED, null));
    }
}
