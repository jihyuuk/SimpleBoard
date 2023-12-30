package com.simple.board.controller;

import com.simple.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class loginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(Authentication authentication){
        //이미 로그인 한 사용자가 /login,/join요청시 리다이렉트 해야함
        if (authentication != null){
            return "/error/logoutPage";
        }
        return "/login/loginForm";
    }

    @GetMapping("/join")
    public String joinForm(Authentication authentication){
        if (authentication != null){
            return "/error/logoutPage";
        }
        return "/login/joinForm";
    }

    @PostMapping("/join")
    public String join(String email,String password, String name){
        userService.join(email,password,name);
        return "redirect:/login";
    }
}
