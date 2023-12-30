package com.simple.board.repository;

import com.simple.board.domain.entity.Category;
import com.simple.board.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Post findByIdAndEnabledTrue(Long id);
}
