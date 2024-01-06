package com.simple.board.repository.postLikes.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class PostLikesRepositoryImpl implements CustomPostLikesRepository{

    private final JPAQueryFactory queryFactory;

    public PostLikesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
