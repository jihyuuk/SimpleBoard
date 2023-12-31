package com.simple.board.service;

import com.simple.board.domain.dto.PostLikeDTO;
import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.User;
import com.simple.board.domain.entity.like.PostLike;
import com.simple.board.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;
    private final UserService userService;

    @Transactional
    public void likeRequest(Long postId, String userName, boolean liked){
        PostLike postLike = postLikeRepository.findByPostIdAndUser_name(postId,userName);

        //좋아요,싫어요 둘 다 x
        if(postLike == null){
            //이부분 굳이또 객체 조회해야하나 고민좀
            Post post = postService.findById(postId);
            User user = userService.findByName(userName);
            postLikeRepository.save(new PostLike(post, user,liked));
            return;
        }

        //좋아요->좋아요 or 싫어요 -> 싫어요
        //즉 취소요청
        if(postLike.isLiked() == liked){
            postLikeRepository.delete(postLike);
        }

        //좋아요 -> 싫어요, 싫어요 -> 좋아요
        //즉 변경요청
        postLike.setLiked(liked);
    }

    public PostLikeDTO getLikeDTO(Long postId, String userName) {
        Long likeCount = postLikeRepository.countByPostIdAndLiked(postId,true);
        Long hateCount = postLikeRepository.countByPostIdAndLiked(postId,false);

        //비로그인시
        if(userName == null){
            return new PostLikeDTO(likeCount,hateCount,null);
        }

        //로그인시
        PostLike postlike = postLikeRepository.findByPostIdAndUser_name(postId, userName);
        Boolean liked = (postlike == null) ? null : postlike.isLiked();

        return new PostLikeDTO(likeCount,hateCount,liked);
    }
}
