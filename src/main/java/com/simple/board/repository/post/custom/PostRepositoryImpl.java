package com.simple.board.repository.post.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simple.board.domain.dto.board.BoardDTO;
import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.*;

import javax.persistence.EntityManager;
import java.util.List;

import static com.simple.board.domain.entity.QCategory.category;
import static com.simple.board.domain.entity.QContent.content;
import static com.simple.board.domain.entity.QPost.post;
import static com.simple.board.domain.entity.QReply.reply;

public class PostRepositoryImpl implements CustomPostRepository {

    JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<BoardDTO> findAllBoardDTO(Category category){

        return queryFactory
                .select(
                        Projections.constructor(BoardDTO.class,
                                post.id,
                                post.title,
                                post.user.name,
                                post.lastModifiedDate,
                                post.likes,
                                JPAExpressions.select(reply.id.count()).from(reply).where(reply.post.eq(post)),
                                post.views))
                .from(post)
                .join(post.user, QUser.user)
                .where(
                        post.category.eq(category),
                        post.enabled.isTrue()
                )
                .fetch();
    }

    @Override
    public PostDTO findPostDTO(Long postId) {

        return queryFactory
                .select(Projections.constructor(PostDTO.class,
                                post,
                                category.name,
                                post.user.name,
                                content.text
                ))
                .from(post)
                .join(post.category, category)
                .join(post.user, QUser.user)
                .join(post.content, content)
                .where(
                        post.id.eq(postId),
                        post.enabled.isTrue()
                )
                .fetchOne();
    }
}
