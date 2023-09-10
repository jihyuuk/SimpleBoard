package com.simple.board.service;

import com.simple.board.entity.Board;
import com.simple.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    public void save(Board board){
        boardRepository.save(board);
    }

}
