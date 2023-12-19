package com.simple.board.domain.entity;

import com.simple.board.domain.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.simple.board.domain.entity.Role.ROLE_USER;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String profile_picture;

    @Enumerated(EnumType.STRING)
    private Role role; //[ROLE_ADMIN],[ROLE_MANAGER],[ROLE_USER]

    private LocalDateTime withdrawal_date;//탈퇴일
    private LocalDateTime last_password_changed;//마지막 비번 변경일

    private boolean withdrawal;//탈퇴여부
    private boolean enabled;//사용가능

    //회원가입시
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;

        //기본셋팅
        this.profile_picture = "기본사진";
        this.enabled = true;
        this.role = ROLE_USER;
        this.last_password_changed = LocalDateTime.now();
    }

    //탈퇴
    public void withdrawal(){
        enabled = false;
        withdrawal = true;
        withdrawal_date = LocalDateTime.now();
    }

    //비번 변경
    public void changePassword(String password){
        this.password = password;
        this.last_password_changed = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }



}
