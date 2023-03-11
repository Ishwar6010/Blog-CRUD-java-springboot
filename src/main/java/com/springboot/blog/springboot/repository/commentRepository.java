package com.springboot.blog.springboot.repository;

import com.springboot.blog.springboot.entity.Comment;
import com.springboot.blog.springboot.entity.post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface commentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId);
}
