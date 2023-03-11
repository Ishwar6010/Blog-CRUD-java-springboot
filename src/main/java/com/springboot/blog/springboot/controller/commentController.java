package com.springboot.blog.springboot.controller;

import com.springboot.blog.springboot.payload.PostResponse;
import com.springboot.blog.springboot.payload.commentDTO;
import com.springboot.blog.springboot.payload.postdto;
import com.springboot.blog.springboot.services.commentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class commentController {
    private commentService commentService;

    public commentController(com.springboot.blog.springboot.services.commentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<commentDTO> createComment(@PathVariable(value = "postId") long postId, @RequestBody commentDTO commentDTO)
    {
        return new ResponseEntity<>(commentService.createDocument(postId,commentDTO),HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<commentDTO> getAllcommentsbyPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<commentDTO> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        commentDTO commentDto = commentService.getCommentByID(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<commentDTO> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody commentDTO commentDto){
        commentDTO updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
