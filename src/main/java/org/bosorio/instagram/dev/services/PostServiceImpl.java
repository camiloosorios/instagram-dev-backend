package org.bosorio.instagram.dev.services;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.bosorio.instagram.dev.entities.Post;
import org.bosorio.instagram.dev.models.PostUser;
import org.bosorio.instagram.dev.repositories.PostRepository;

import java.util.Base64;
import java.util.List;

@WebService(endpointInterface = "org.bosorio.instagram.dev.services.PostService")
public class PostServiceImpl implements PostService {

    @Inject
    private PostRepository postRepository;

    @Inject
    private ImageService imageService;

    @Inject
    private JWTService jwtService;

    @Override
    @WebMethod
    public void create(Post post, String token) {
        jwtService.validateToken(token);
        postRepository.create(post);
    }

    @Override
    @WebMethod
    public List<Post> findUserPosts(Long userId, String token) {
        return postRepository.findUserPosts(userId);
    }

    @Override
    @WebMethod
    public List<PostUser> findFollowingPosts(Long userId, String token) {
        List<PostUser> posts = postRepository.findFollowingPosts(userId);
        posts.forEach(post -> {
            String image = Base64.getEncoder().encodeToString(imageService.getImage(post.getImage(), token));
            post.setImage(image);
        });

        return posts;
    }

    @Override
    @WebMethod
    public Post findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    @WebMethod
    public void update(Post post, String token) {
        jwtService.validateToken(token);
        if (post.getId() == null) {
            throw new RuntimeException("Post id is required");
        }
        Post currentPost = postRepository.findById(post.getId());

        if (currentPost == null) {
            throw new RuntimeException("Post doesn't exist");
        }

        post.setCreatedAt(currentPost.getCreatedAt());
        postRepository.update(post);
    }

    @Override
    @WebMethod
    public void delete(Long id, String token) {
        jwtService.validateToken(token);
        postRepository.delete(id);
    }

}
