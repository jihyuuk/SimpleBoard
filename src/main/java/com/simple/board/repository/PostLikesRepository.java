package com.simple.board.repository;

import com.simple.board.domain.entity.like.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    PostLikes findByPostIdAndUser_name(Long postId, String name);

    boolean existsByPostIdAndUser_nameAndIsLiked(Long postId, String name, boolean isLiked);

}
