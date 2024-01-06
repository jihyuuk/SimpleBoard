package com.simple.board.repository.post.custom;

import com.simple.board.domain.dto.board.BoardDTO;
import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.dto.post.PostFormDTO;
import com.simple.board.domain.entity.Category;

import java.util.List;

public interface CustomPostRepository {
    List<BoardDTO> findAllBoardDTO(Category category);

    PostDTO findPostDTO(Long postId);

    PostFormDTO findByIdAndUser_name(Long id, String name);

}
