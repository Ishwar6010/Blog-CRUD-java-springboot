package com.springboot.blog.springboot.repository;

import com.springboot.blog.springboot.entity.post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postRepository extends JpaRepository<post,Long> {

}
