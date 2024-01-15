package com.simple.board.service;

import com.simple.board.domain.entity.Content;
import com.simple.board.domain.entity.PostImage;
import com.simple.board.repository.content.ContentRepository;
import com.simple.board.repository.postImage.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {
    
    private final PostImageRepository postImageRepository;

    public void saveList(Content content, List<String> images){
        for (String url : images) {
            PostImage postImage = new PostImage(content,url);
            postImageRepository.save(postImage);
        }
    }
    
    
}
