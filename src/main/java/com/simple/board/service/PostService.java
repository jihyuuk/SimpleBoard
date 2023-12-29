package com.simple.board.service;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.Post;
import com.simple.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDTO findDTOById(Long id){
        Optional<Post> opPost = postRepository.findById(id);
        Post post = opPost.get();
        return new PostDTO(post);
    }

}
