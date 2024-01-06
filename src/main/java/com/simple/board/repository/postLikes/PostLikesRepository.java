package com.simple.board.repository.postLikes;

import com.simple.board.domain.entity.like.PostLikes;
import com.simple.board.repository.postLikes.custom.CustomPostLikesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long>, CustomPostLikesRepository {

    PostLikes findByPostIdAndUser_name(Long postId, String name);

}
