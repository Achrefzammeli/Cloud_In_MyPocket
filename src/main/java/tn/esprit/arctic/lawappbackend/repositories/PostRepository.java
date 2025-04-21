package tn.esprit.arctic.lawappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.arctic.lawappbackend.entities.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
