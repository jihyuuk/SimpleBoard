package com.simple.board.repository.reply.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simple.board.domain.dto.Reply.ReplyDTO;
import com.simple.board.domain.entity.QUser;

import javax.persistence.EntityManager;
import java.util.List;

import static com.simple.board.domain.entity.QReply.reply;

public class ReplyRepositoryImpl implements CustomReplyRepository {

    JPAQueryFactory queryFactory;

    public ReplyRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ReplyDTO> findALLReplyDTO(Long postId) {
        return queryFactory
                .select(Projections.constructor(ReplyDTO.class,
                        reply.id,
                        reply.user.name,
                        reply.comment,
                        reply.likes,
                        reply.hates,
                        reply.lastModifiedDate
                ))
                .from(reply)
                .join(reply.user, QUser.user)
                .where(reply.post.id.eq(postId),(reply.enabled).isTrue())
                .orderBy(reply.createdDate.asc())
                .fetch();
    }
}
