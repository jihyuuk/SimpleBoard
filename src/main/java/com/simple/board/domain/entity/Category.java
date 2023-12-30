package com.simple.board.domain.entity;

import com.simple.board.domain.dto.post.PostDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private boolean enabled;
    
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    
    //enabled = true인 게시글만 가져오기
    public List<PostDTO> getEnabledPostDTOS(){
        return posts.stream()
                .filter(Post::isEnabled)
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    public Category(String name) {
        this.name = name;
        enabled = true;
    }
}
