package com.simple.board.repository.postImage;


import com.simple.board.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
