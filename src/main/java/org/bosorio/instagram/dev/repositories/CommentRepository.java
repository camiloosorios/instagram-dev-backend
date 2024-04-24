package org.bosorio.instagram.dev.repositories;

import org.bosorio.instagram.dev.entities.Comment;
import org.bosorio.instagram.dev.models.PostComment;

import java.util.List;

public interface CommentRepository {

    void create(Comment comment);

    List<PostComment> findPostComments(Long postId);

    Comment findById(Long id);

    void update(Comment comment);

    void delete(Long id);
}
