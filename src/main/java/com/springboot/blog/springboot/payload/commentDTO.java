package com.springboot.blog.springboot.payload;

import com.springboot.blog.springboot.entity.post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class commentDTO {
    private long id;
    private String name;
    private String emailId;
    private String body;
}
