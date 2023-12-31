package org.gachon.checkmate.domain.post.repository;

import org.gachon.checkmate.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    boolean existsByTitle(String title);
}
