package com.simple.board.repository.reply;

import com.simple.board.domain.dto.Reply.ReplyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    void getRepliesByQueryDSL(){
        List<ReplyDTO> result = replyRepository.findALLReplyDTO(10L);
        assertThat(result.size()).isEqualTo(2);
    }

}