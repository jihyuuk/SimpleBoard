package com.simple.board.controller;

import com.simple.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class loginController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String loginForm(){
        return "/login/loginForm";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "/login/joinForm";
    }

    @PostMapping("/join")
    public String join(String email,String password, String name){
        userService.join(email,bCryptPasswordEncoder.encode(password),name);
        return "redirect:/login";
    }
}
