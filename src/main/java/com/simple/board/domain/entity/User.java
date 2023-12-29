package com.simple.board.domain.entity;

import com.simple.board.domain.auditing.BaseTime;
import com.simple.board.domain.enums.ROLE;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "user_id")
    Long id;

    String email;
    String password;
    String picture;
    String name;

    @Enumerated(EnumType.STRING)
    ROLE role;
    boolean enabled;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;

        picture = "default/picture";
        role = ROLE.ROLE_USER;
        enabled = true;
    }
}
