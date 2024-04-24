package org.bosorio.instagram.dev.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.bosorio.instagram.dev.entities.User;

import java.util.List;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User" +
                                "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                                "FROM User u", User.class)
                .getResultList();
    }

    @Override
    public List<User> findByUsernameOrFullName(String param) {
        try {
            return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User " +
                            "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                            "FROM User u WHERE u.username LIKE CONCAT('%', :param, '%') " +
                            "OR u.name LIKE CONCAT('%', :param, '%') " +
                            "OR u.lastname LIKE CONCAT('%', :param, '%')", User.class)
                    .setParameter("param", param)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User" +
                    "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                    "FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User" +
                            "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                            "FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User" +
                            "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                            "FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findUserWithPasswordById(Long id) {
        try {
            return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User" +
                            "(u.id, u.username, u.name, u.lastname, u.email, u.password, u.createdAt) " +
                            "FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void follow(Long followerId, Long followingId) {
        em.createNativeQuery("INSERT INTO followers (follower_id, followed_id) VALUES (:follower_id, :followed_id)")
                .setParameter("follower_id", followerId)
                .setParameter("followed_id", followingId)
                .executeUpdate();
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        em.createNativeQuery("DELETE FROM followers WHERE follower_id = :follower_id AND followed_id = :followed_id")
                .setParameter("follower_id", followerId)
                .setParameter("followed_id", followingId)
                .executeUpdate();
    }

    @Override
    public List<User> findFollowing(Long userId) {
        return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User " +
                        "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                        "FROM User u JOIN u.followers f WHERE follower_id = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<User> findFollowers(Long userId) {
        return em.createQuery("SELECT NEW org.bosorio.instagram.dev.entities.User " +
                        "(u.id, u.username, u.name, u.lastname, u.email, u.createdAt) " +
                        "FROM User u JOIN u.following f WHERE followed_id = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

}
