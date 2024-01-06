package com.simple.board.service;

import com.simple.board.domain.entity.Content;
import com.simple.board.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public void save(Content content){
        contentRepository.save(content);
    }

}
