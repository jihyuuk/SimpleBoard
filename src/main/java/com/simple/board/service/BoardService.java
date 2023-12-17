package com.simple.board.service;

import com.simple.board.domain.entity.Post;
import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.dto.post.PostNewDTO;
import com.simple.board.domain.dto.post.PostUpdateDTO;
import com.simple.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<PostDTO> findByPage(Pageable pageable){
        Page<Post> result = boardRepository.findAll(pageable);
        return result.map(PostDTO::new);
    }

    public void deleteById(Long id){
        boardRepository.deleteById(id);
    }


}
