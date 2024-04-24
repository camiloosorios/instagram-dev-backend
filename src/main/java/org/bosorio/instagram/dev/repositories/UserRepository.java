package org.bosorio.instagram.dev.repositories;

import org.bosorio.instagram.dev.entities.User;

import java.util.List;

public interface UserRepository {

    void create(User user);

    List<User> findAll();

    List<User> findByUsernameOrFullName(String param);
    User findById(Long id);

    void update(User user);

    void delete(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findUserWithPasswordById(Long id);

    void follow(Long followerId, Long followingId);

    void unfollow(Long followerId, Long followingId);

    List<User> findFollowing(Long userId);

    List<User> findFollowers(Long userId);

}
