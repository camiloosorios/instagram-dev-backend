package org.bosorio.instagram.dev.services;

import jakarta.jws.WebService;
import org.bosorio.instagram.dev.entities.Post;
import org.bosorio.instagram.dev.models.PostUser;

import java.util.List;

@WebService
public interface PostService {

    void create(Post post, String token);

    List<Post> findUserPosts(Long postId, String token);

    List<PostUser> findFollowingPosts(Long userId, String token);

    Post findById(Long id);

    void update(Post post, String token);

    void delete(Long id, String token);

}
