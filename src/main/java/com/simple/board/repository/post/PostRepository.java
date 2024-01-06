package com.simple.board.repository.post;

import com.simple.board.domain.dto.post.PostFormDTO;
import com.simple.board.domain.entity.Category;
import com.simple.board.domain.entity.Post;
import com.simple.board.repository.post.custom.CustomPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>, CustomPostRepository {
    Post findByIdAndEnabledTrue(Long id);

    PostFormDTO findByIdAndUser_name(Long id, String name);
}
