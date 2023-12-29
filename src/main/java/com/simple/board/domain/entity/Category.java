package com.simple.board.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    Long id;

    String name;
    boolean enable;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();

    public Category(String name) {
        this.name = name;
        enable = true;
    }
}
