package com.springboot.blog.springboot.payload;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class postdto {
    private long id;
    private String title;
    private String description;
    private String content;
}
