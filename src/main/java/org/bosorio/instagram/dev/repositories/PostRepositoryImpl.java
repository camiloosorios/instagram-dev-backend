package org.bosorio.instagram.dev.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.bosorio.instagram.dev.entities.Post;
import org.bosorio.instagram.dev.models.PostUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
public class PostRepositoryImpl implements PostRepository {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public void create(Post post) {
        em.persist(post);
    }

    @Override
    public List<Post> findUserPosts(Long userId) {
        return em.createQuery("SELECT p FROM Post p where p.userId = :userId", Post.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<PostUser> findFollowingPosts(Long userId) {
        Query query = em.createNativeQuery("SELECT p.id, u.username, p.image, p.description, p.created_at as createdAt " +
                "FROM posts p " +
                "LEFT OUTER JOIN users u ON p.user_id = u.id " +
                "JOIN followers f ON u.id = f.followed_id " +
                "WHERE f.follower_id = :userId");

        query.setParameter("userId", userId);

        List<Object[]> resultList = query.getResultList();

        List<PostUser> posts = new ArrayList<>();
        for (Object[] row : resultList) {
            PostUser post = new PostUser();
            post.setId(Long.parseLong(row[0].toString()));
            post.setUser(row[1].toString());
            post.setImage(row[2].toString());
            post.setDescription(row[3].toString());
            post.setCreatedAt((Date) row[4]);
            posts.add(post);
        }

        return posts;
    }

    @Override
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    @Override
    @Transactional
    public void update(Post post) {
        em.merge(post);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Post post = findById(id);
        em.remove(post);
    }
}
