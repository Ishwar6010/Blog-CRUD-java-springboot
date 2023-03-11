package com.springboot.blog.springboot.controller;

import com.springboot.blog.springboot.payload.PostResponse;
import com.springboot.blog.springboot.payload.postdto;
import com.springboot.blog.springboot.services.postService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class postController {
    private postService postService;

    public postController(postService postService) {
        this.postService = postService;
    }
    //create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<postdto> createPost(@RequestBody postdto postDTO)
    {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }
    // get all posts rest api

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy);
    }
    @GetMapping("/{id}")
    public ResponseEntity<postdto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostbyId(id));
    }

    // update post by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<postdto> updatePost(@RequestBody postdto postDto, @PathVariable(name = "id") long id){

        postdto postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }


}
