package com.simple.board.service;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.Category;
import com.simple.board.domain.entity.Content;
import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ContentService contentService;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostDTO findDTOById(Long id){
        Optional<Post> opPost = postRepository.findById(id);
        Post post = opPost.get();
        return new PostDTO(post);
    }

    public Post findById(Long id){
        return postRepository.findById(id).get();
    }

    @Transactional
    public void update(Long postId,String title,String text){
        Post post = postRepository.findById(postId).get();
        post.setTitle(title);
        post.getContent().setText(text);
    }

    @Transactional
    public Long save(Long categoryId, String title, String text){
        User user = userService.findByName("userA");

        Content content = new Content(text);
        contentService.save(content);

        Category category = categoryService.findById(categoryId);

        Post post = new Post(category, user, title,content);
        return postRepository.save(post).getId();
    }

}
