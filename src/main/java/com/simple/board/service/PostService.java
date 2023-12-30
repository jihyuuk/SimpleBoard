package com.simple.board.service;

import com.simple.board.domain.entity.Category;
import com.simple.board.domain.entity.Content;
import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ContentService contentService;
    private final UserService userService;
    private final CategoryService categoryService;

    public Post findById(Long id){
        //enabled=true인 게시글만
        return postRepository.findByIdAndEnabledTrue(id);
    }

    @Transactional
    public void update(Long postId,String title,String text,String accessUser){
        //enabled=true인 게시글만
        Post post = findById(postId);

        beforeUpdateCheck(accessUser, post);

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
        Post post = findById(id);

        beforeUpdateCheck(accessUser, post);
        
        post.setEnabled(false);
    }

    //작성자인지 확인
    private boolean isAuthor(String accessUser, Post post) {
        return post.getUser().getName().equals(accessUser);
    }

    private void beforeUpdateCheck(String accessUser, Post post) {
        //이미 삭제되었거나 존재x -> 404에러
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //작성자만이 업데이트 가능 -> 403에러
        if(!isAuthor(accessUser, post)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
