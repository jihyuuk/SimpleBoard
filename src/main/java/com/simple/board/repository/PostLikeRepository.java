package com.simple.board.repository;

import com.simple.board.domain.entity.like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findByPostIdAndUser_name(Long postId,String name);

    Long countByPostIdAndLiked(Long postId,boolean liked);
}
