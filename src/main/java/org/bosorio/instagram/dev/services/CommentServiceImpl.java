package org.bosorio.instagram.dev.services;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import org.bosorio.instagram.dev.entities.Comment;
import org.bosorio.instagram.dev.models.PostComment;
import org.bosorio.instagram.dev.repositories.CommentRepository;

import java.util.List;

@WebService(endpointInterface = "org.bosorio.instagram.dev.services.CommentService")
public class CommentServiceImpl implements CommentService {

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private JWTService jwtService;

    @Resource
    WebServiceContext webServiceContext;

    @Override
    @WebMethod
    public void create(Comment comment, String token) {
        jwtService.validateToken(token);
        commentRepository.create(comment);
    }

    @Override
    @WebMethod
    public List<PostComment> findPostComments(Long postId) {
        return commentRepository.findPostComments(postId);
    }

    @Override
    @WebMethod
    public Comment findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @WebMethod
    public void update(Comment comment, String token) {
        jwtService.validateToken(token);
        if (comment.getId() == null) {
            throw new RuntimeException("Invalid comment id");
        }

        Comment currentComment = commentRepository.findById(comment.getId());

        if (currentComment == null) {
            throw new RuntimeException("Comment doesn't exist");
        }

        comment.setCreatedAt(currentComment.getCreatedAt());

        commentRepository.update(comment);
    }

    @Override
    @WebMethod
    public void delete(Long id, String token) {
        jwtService.validateToken(token);
        commentRepository.delete(id);
    }
}
