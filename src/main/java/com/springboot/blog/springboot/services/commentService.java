package com.springboot.blog.springboot.services;

import com.springboot.blog.springboot.payload.commentDTO;
import java.util.List;

public interface commentService {
    commentDTO createDocument(long postId, commentDTO commentdto);

    List<commentDTO> getCommentsByPostId(long postId);
    commentDTO getCommentByID(Long postId, Long commentId);
    commentDTO updateComment(Long postId, long commentId, commentDTO commentRequest);
    void deleteComment(Long postId, Long commentId);
}
