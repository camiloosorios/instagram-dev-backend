package org.bosorio.instagram.dev.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.bosorio.instagram.dev.entities.Comment;
import org.bosorio.instagram.dev.models.PostComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
public class CommentRepositoryImpl implements CommentRepository {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public void create(Comment comment) {
        em.persist(comment);
    }

    @Override
    public List<PostComment> findPostComments(Long postId) {
        Query query = em.createNativeQuery("SELECT c.id, c.content, c.post_id, u.username as user, c.created_at as createdAt " +
                "FROM comments c " +
                "LEFT OUTER JOIN users u ON c.user_id = u.id " +
                "WHERE c.post_id = :postId");

        query.setParameter("postId", postId);

        List<Object[]> resultList = query.getResultList();

        List<PostComment> comments = new ArrayList<>();
        for (Object[] row : resultList) {
            PostComment comment = new PostComment();
            comment.setId(Long.parseLong(row[0].toString()));
            comment.setContent((String) row[1]);
            comment.setPostId(Long.parseLong(row[2].toString()));
            comment.setUser((String) row[3]);
            comment.setCreatedAt((Date) row[4]);
            comments.add(comment);
        }

        return comments;
    }

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Comment comment = findById(id);
        em.remove(comment);
    }
}
