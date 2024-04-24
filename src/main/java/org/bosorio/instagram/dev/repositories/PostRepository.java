package org.bosorio.instagram.dev.repositories;

import org.bosorio.instagram.dev.entities.Post;
import org.bosorio.instagram.dev.models.PostUser;

import java.util.List;

public interface PostRepository {

    void create(Post post);

    List<Post> findUserPosts(Long userId);

    List<PostUser> findFollowingPosts(Long userId);

    Post findById(Long id);

    void update(Post post);

    void delete(Long id);

}
