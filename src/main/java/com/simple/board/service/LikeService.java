package com.simple.board.service;

import com.simple.board.domain.dto.like.PostLikeDTO;
import com.simple.board.domain.dto.like.ReplyLikeDTO;
import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.Reply;
import com.simple.board.domain.entity.User;
import com.simple.board.domain.entity.like.PostLikes;
import com.simple.board.domain.entity.like.ReplyLikes;
import com.simple.board.repository.PostLikesRepository;
import com.simple.board.repository.ReplyLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikesRepository postLikesRepository;
    private final ReplyLikesRepository replyLikesRepository;
    private final PostService postService;
    private final ReplyService replyService;
    private final UserService userService;

    @Transactional
    public void postLikeRequest(Long postId, String userName, boolean isLiked){
        PostLikes postLikes = postLikesRepository.findByPostIdAndUser_name(postId,userName);

        //이부분 굳이또 객체 조회해야하나 고민좀
        Post post = postService.findById(postId);
        User user = userService.findByName(userName);

        //좋아요,싫어요 둘 다 x
        if(postLikes == null){
            //post랑,postLike테이블에 각각 저장
            //이분 post로 옮길까
            if(isLiked){
                post.setLikes(post.getLikes()+1);
            }else {
                post.setHates(post.getHates()+1);
            }
            postLikesRepository.save(new PostLikes(post, user,isLiked));
            return;
        }

        //좋아요->좋아요 or 싫어요 -> 싫어요
        //즉 취소요청
        if(postLikes.isLiked() == isLiked){
            if(isLiked){
                post.setLikes(post.getLikes()-1);
            }else{
                post.setHates(post.getHates()-1);
            }
            postLikesRepository.delete(postLikes);
            return;
        }

        //좋아요 -> 싫어요, 싫어요 -> 좋아요
        //즉 변경요청
        if(isLiked){
            //좋아요 +1
            //싫어요 -1
            post.setLikes(post.getLikes()+1);
            post.setHates(post.getHates()-1);
        }else{
            //싫어요 +1
            //좋아요 -1
            post.setLikes(post.getLikes()-1);
            post.setHates(post.getHates()+1);
        }
        postLikes.setLiked(isLiked);
    }

    public PostLikeDTO getPostLikeDTO(Long postId, String userName) {
        //방법 1. postId,userName으로 postLikes 튜플 찾아서 분석하기 // 쿼리 1번
        //방법 2. exits로 존재 여부 확인하기 // 쿼리 2번
        //선택 : 2
        if(userName == null) return new PostLikeDTO(false,false);

        boolean isLiked = postLikesRepository.existsByPostIdAndUser_nameAndIsLiked(postId, userName, true);
        boolean isHated= postLikesRepository.existsByPostIdAndUser_nameAndIsLiked(postId, userName, false);
        return  new PostLikeDTO(isLiked,isHated);
    }

    @Transactional
    public void replyLikeRequest(Long replyId, String userName, boolean isLiked){
        ReplyLikes replyLikes = replyLikesRepository.findByReplyIdAndUser_name(replyId, userName);

        //이부분 굳이또 객체 조회해야하나 고민좀 =>get가능
        Reply reply = replyService.findById(replyId);
        User user = userService.findByName(userName);

        //좋아요,싫어요 둘 다 x
        if(replyLikes == null){
            if(isLiked){
                reply.setLikes(reply.getLikes()+1);
            }else {
                reply.setHates(reply.getHates()+1);
            }
            replyLikesRepository.save(new ReplyLikes(reply,user,isLiked));
            return;
        }

        //좋아요->좋아요 or 싫어요 -> 싫어요
        //즉 취소요청
        if(replyLikes.isLiked() == isLiked){
            if(isLiked){
                reply.setLikes(reply.getLikes()-1);
            }else{
                reply.setHates(reply.getHates()-1);
            }
            replyLikesRepository.delete(replyLikes);
            return;
        }

        //좋아요 -> 싫어요, 싫어요 -> 좋아요
        //즉 변경요청
        if(isLiked){
            //좋아요 +1
            //싫어요 -1
            reply.setLikes(reply.getLikes()+1);
            reply.setHates(reply.getHates()-1);
        }else{
            //싫어요 +1
            //좋아요 -1
            reply.setLikes(reply.getLikes()-1);
            reply.setHates(reply.getHates()+1);
        }
        replyLikes.setLiked(isLiked);
    }

    public List<Long> getUserLikedReplyIds(List<Reply> replies,String userName){
        List<ReplyLikes> list = replyLikesRepository.findAllByReplyInAndUser_nameAndIsLiked(replies, userName, true);
        return list.stream().map(rk -> rk.getReply().getId()).collect(Collectors.toList());
    }

    public List<Long> getUserHatedReplyIds(List<Reply> replies,String userName){
        List<ReplyLikes> list = replyLikesRepository.findAllByReplyInAndUser_nameAndIsLiked(replies, userName, false);
        return list.stream().map(rk -> rk.getReply().getId()).collect(Collectors.toList());
    }

}
