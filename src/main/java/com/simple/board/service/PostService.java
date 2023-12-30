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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ContentService contentService;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostDTO findDTOById(Long id){
        //enabled=true인 게시글만
        Post post = postRepository.findByIdAndEnabledTrue(id);
        return new PostDTO(post);
    }

    public Post findById(Long id){
        return postRepository.findByIdAndEnabledTrue(id);
    }

    @Transactional
    public void update(Long postId,String title,String text,String accessUser){
        //enabled=true인 게시글만
        Post post = postRepository.findByIdAndEnabledTrue(postId);
        
        //작성자만이 업데이트 가능
        if(!post.getUser().getName().equals(accessUser)){
            return;
        }
        post.setTitle(title);
        post.getContent().setText(text);
    }

    @Transactional
    public Long save(Long categoryId, String title, String text, String userName){
        Category category = categoryService.findById(categoryId);
        User user = userService.findByName(userName);
        //이미 isAuthenticated()로 확인했지만
        //USER가 없을시 예외처리해야함, cateogry도 마찬가지
        Content content = new Content(text);
        contentService.save(content);

        Post post = new Post(category, user, title,content);
        return postRepository.save(post).getId();
    }

    @Transactional
    public void delete(Long id, String accessUser) {
        //논리적삭제 enabled = false로 논리적삭제
        Post post = postRepository.findById(id).get();
        //작성자만이 업데이트 가능
        if(!post.getUser().getName().equals(accessUser)){
            return;
        }
        post.setEnabled(false);
    }
}
