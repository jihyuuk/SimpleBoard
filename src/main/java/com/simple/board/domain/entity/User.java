package com.simple.board.domain.entity;

import com.simple.board.domain.auditing.BaseTime;
import com.simple.board.domain.enums.ROLE;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String picture;
    private String name;

    @Enumerated(EnumType.STRING)
    private ROLE role;//[ROLE_ADMIN],[ROLE_MANAGER],[ROLE_USER]
    private boolean enabled;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;

        picture = "default/picture";
        role = ROLE.ROLE_USER;
        enabled = true;
    }

}
