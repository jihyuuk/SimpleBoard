package com.simple.board.service;

import com.simple.board.entity.Board;
import com.simple.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    public void save(Board board){
        boardRepository.save(board);
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    public Board findById(int id){
        return boardRepository.findById(id).get();
    }

    public Page<Board> findByPage(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

}
