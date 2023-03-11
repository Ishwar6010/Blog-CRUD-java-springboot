package com.springboot.blog.springboot.services.imp;

import com.springboot.blog.springboot.entity.post;
import com.springboot.blog.springboot.exception.ResourceNotFoundException;
import com.springboot.blog.springboot.payload.PostResponse;
import com.springboot.blog.springboot.payload.postdto;
import com.springboot.blog.springboot.repository.postRepository;
import com.springboot.blog.springboot.services.postService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImp implements postService {
    private postRepository postRepo;
    public postServiceImp(postRepository postRepo)
    {
        this.postRepo = postRepo;
    }
    @Override
    public postdto createPost(postdto postDTO) {
        // convert DTO to entity
        post post = mapToEntity(postDTO);
        post newPost = postRepo.save(post);

        // convert entity to DTO
        postdto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<post> posts = postRepo.findAll(pageable);

        // get content for page object
        List<post> listOfPosts = posts.getContent();

        List<postdto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public postdto getPostbyId(long postId) {
        post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        postdto postResponse = mapToDTO(post);
        return postResponse;
    }

    @Override
    public postdto updatePost(postdto postDto, long postId) {
        post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        post updatedPost = postRepo.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long postId) {
        post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        postRepo.delete(post);
    }

    // convert Entity into DTO
    private postdto mapToDTO(post post){
        postdto postDto = new postdto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert DTO to entity
    private post mapToEntity(postdto postDto){
        post post = new post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
