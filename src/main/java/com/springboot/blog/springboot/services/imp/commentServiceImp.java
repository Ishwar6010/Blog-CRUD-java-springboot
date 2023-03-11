package com.springboot.blog.springboot.services.imp;

import com.springboot.blog.springboot.entity.Comment;
import com.springboot.blog.springboot.entity.post;
import com.springboot.blog.springboot.exception.BlogAPIException;
import com.springboot.blog.springboot.exception.ResourceNotFoundException;
import com.springboot.blog.springboot.payload.commentDTO;
import com.springboot.blog.springboot.repository.commentRepository;
import com.springboot.blog.springboot.repository.postRepository;
import com.springboot.blog.springboot.services.commentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class commentServiceImp implements commentService {
    private commentRepository commentRepo;
    private postRepository postRepo;

    public commentServiceImp(commentRepository commentRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @Override
    public commentDTO createDocument(long postId, commentDTO commentdto) {
        Comment comment = mapToEntity(commentdto);
        //retrieve post by id
        post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //
        comment.setPost((post));
        Comment newComment = commentRepo.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<commentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment->mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public commentDTO getCommentByID(Long postId, Long commentId) {
        // retrieve post entity by id
        post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(comment.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public commentDTO updateComment(Long postId, long commentId, commentDTO commentRequest) {
        // retrieve post entity by id
        post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(comment.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmailId(commentRequest.getEmailId());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDTO(updatedComment);
    }
    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        if(comment.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepo.delete(comment);
    }
    // convert Entity into DTO
    private commentDTO mapToDTO(Comment comment){
        commentDTO commentdto = new commentDTO();
        commentdto.setId(comment.getId());
        commentdto.setBody(comment.getBody());
        commentdto.setName(comment.getName());
        commentdto.setEmailId(comment.getEmailId());
        return commentdto;
    }

    // convert DTO to entity
    private Comment mapToEntity(commentDTO commentdto){
       Comment comment = new Comment();
       comment.setBody(commentdto.getBody());
       comment.setName(commentdto.getName());
       comment.setEmailId(commentdto.getEmailId());
       comment.setName(commentdto.getName());
       return comment;
    }
}
