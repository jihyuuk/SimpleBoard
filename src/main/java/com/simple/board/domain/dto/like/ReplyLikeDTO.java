package com.simple.board.domain.dto.like;
import com.simple.board.domain.entity.like.ReplyLikes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ReplyLikeDTO {

    private Map<Long,Boolean> map = new HashMap<>();

    public ReplyLikeDTO (List<ReplyLikes> list){
        list.forEach(replyLikes -> map.put(replyLikes.getReply().getId(),replyLikes.isLiked()));
    }

    public boolean isLiked(Long replyId){
        Boolean reaction = map.get(replyId);
        return reaction != null && reaction.equals(true);
    }

    public boolean isHated(Long replyId){
        Boolean reaction = map.get(replyId);
        return reaction != null && reaction.equals(false);
    }

}
