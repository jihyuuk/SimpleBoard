package com.simple.board.service;

import com.simple.board.domain.Post;
import com.simple.board.model.PostNewDTO;
import com.simple.board.model.PostUpdateDTO;
import com.simple.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        int pageNumber = pageable.getPageNumber()-1;//보여줄 페이지
        int pageSize = 10;//한 페이지 안에 보여줄 글 개수
        return boardRepository.findAll(PageRequest.of(pageNumber,pageSize, Sort.Direction.DESC, "id"));
    }

    public void deleteById(Long id){
        boardRepository.deleteById(id);
    }


}
