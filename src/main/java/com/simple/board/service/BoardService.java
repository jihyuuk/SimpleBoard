package com.simple.board.service;

import com.simple.board.domain.Post;
import com.simple.board.model.PostNewDTO;
import com.simple.board.model.PostUpdateDTO;
import com.simple.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long save(PostNewDTO postNewDTO){
        Post post = new Post(postNewDTO);
        boardRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void update(PostUpdateDTO updateDTO){
        Optional<Post> find = boardRepository.findById(updateDTO.getId());
        //나중에 optional처리
        Post post = find.get();

        post.update(updateDTO);
    }

    public List<Post> findAll(){
        return boardRepository.findAll();
    }

    public Post findById(Long id){
        return boardRepository.findById(id).get();
    }

    public Page<Post> findByPage(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public void deleteById(Long id){
        boardRepository.deleteById(id);
    }


}
