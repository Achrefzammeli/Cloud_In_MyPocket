package tn.esprit.arctic.lawappbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import tn.esprit.arctic.lawappbackend.entities.Comment;
import tn.esprit.arctic.lawappbackend.repositories.CommentRepository;
import tn.esprit.arctic.lawappbackend.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

public interface CommentService {
// Create
Comment saveComment(Comment comment);

        // Read
        Optional<Comment> getCommentById(Long id);
        List<Comment> getAllComments();
    Page<Comment> findByPostId(@Param("postId") Long postId, Pageable pageable);

        // Update
        Comment updateComment(Long id, Comment updatedComment);

        // Delete
        void deleteCommentById(Long id);

        // Additional business logic methods
        boolean existsById(Long id);
        long countCommentsByPostId(Long postId);

    Page<Comment> getCommentsByPostId(Long postId, Pageable pageable);
}
