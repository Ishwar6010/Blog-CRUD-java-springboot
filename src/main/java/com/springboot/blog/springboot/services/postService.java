package com.springboot.blog.springboot.services;

import com.springboot.blog.springboot.payload.PostResponse;
import com.springboot.blog.springboot.payload.postdto;

import java.util.List;

public interface postService {
    postdto createPost(postdto postDTO);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);
    postdto getPostbyId(long postId);
    postdto updatePost(postdto postDto, long postId);

    void deletePostById(long postId);
}
