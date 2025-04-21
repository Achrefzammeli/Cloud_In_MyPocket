package tn.esprit.arctic.lawappbackend.services;

import org.springframework.stereotype.Service;
import tn.esprit.arctic.lawappbackend.entities.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);
    Post getPostById(Long id);
    List<Post> getAllPosts();
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    Post likePost(Long postId);

    void resetDailyViews();
}
