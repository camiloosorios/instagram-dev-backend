package org.bosorio.instagram.dev.services;

import jakarta.jws.WebService;
import org.bosorio.instagram.dev.entities.User;

import java.util.List;

@WebService
public interface UserService {

    String login(String email, String password);

    boolean verifyToken(String token);

    String create(User user);

    List<User> findAll();

    User findById(Long id);

    List<User> findByUsernameOrFullName(String param);

    User findByUsername(String username);

    User findByEmail(String email);

    void follow(Long follower_id, Long follow_id, String token);

    void unfollow(Long follower_id, Long follow_id, String token);

    List<User> findFollowing(Long userId);

    List<User> findFollowers(Long userId);

    void update(User user, String token);

    void delete(Long id, String token);

}
