package org.bosorio.instagram.dev.services;

import jakarta.jws.WebService;
import org.bosorio.instagram.dev.entities.Comment;
import org.bosorio.instagram.dev.models.PostComment;

import java.util.List;

@WebService
public interface CommentService {

    void create(Comment comment, String token);

    List<PostComment> findPostComments(Long postId);

    Comment findById(Long id);

    void update(Comment comment, String token);

    void delete(Long id, String token);

}
