package com.simple.board.repository.post.custom;

import com.simple.board.domain.dto.board.BoardDTO;
import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.Category;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.category.CategoryRepository;
import com.simple.board.repository.post.PostRepository;
import com.simple.board.repository.user.UserRepository;
import com.simple.board.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeService likeService;

    @Test
    void findAllBoardDTO(){
        Category category1 = categoryRepository.findByName("커뮤니티");
        Category category2 = categoryRepository.findByName("공지사항");
        List<BoardDTO> result1 = postRepository.findAllBoardDTO(category1);
        List<BoardDTO> result2 = postRepository.findAllBoardDTO(category2);
        assertThat(result1.size()).isEqualTo(1);
        assertThat(result2.size()).isEqualTo(0);
    }

    @Test
    void subQuery(){
        Category category = categoryRepository.findByName("커뮤니티");
        List<BoardDTO> result = postRepository.findAllBoardDTO(category);
        assertThat(result.get(0).getReplys()).isEqualTo(2);
    }

    @Test
    void findPostDTO(){
        PostDTO result = postRepository.findPostDTO(10L);
    }


//    @Test
//    void findPostDTOLikeCheck(){
//        PostDTO result = postRepository.findPostDTO(10L);
//
//        //아무것도 안눌렀을때
//        assertThat(result.isHated()).isFalse();
//        assertThat(result.isLiked()).isFalse();
//
//        //좋아요 눌렀을때
//        likeService.postLikeRequest(10L,"userA",true);
//        PostDTO result2 = postRepository.findPostDTO(10L);
//        assertThat(result2.isHated()).isFalse();
//        assertThat(result2.isLiked()).isTrue();
//
//        //싫어요 눌렀을때
//        likeService.postLikeRequest(10L,"userA",false);
//        PostDTO result3 = postRepository.findPostDTO(10L);
//        assertThat(result3.isHated()).isTrue();
//        assertThat(result3.isLiked()).isFalse();
//    }

}