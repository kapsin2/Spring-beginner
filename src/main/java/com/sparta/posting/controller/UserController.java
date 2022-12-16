package com.sparta.posting.controller;

import com.sparta.posting.dto.LoginRequestDto;
import com.sparta.posting.dto.LoginResponseDto;
import com.sparta.posting.dto.SignupRequestDto;
import com.sparta.posting.dto.SignupResponseDto;
import com.sparta.posting.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {      //@Valid로 SignupRequestDto에 제한식을 걸어서 회원가입한다.
        userService.signup(signupRequestDto);
        SignupResponseDto signupResponseDto = new SignupResponseDto();   //회원가입 성공메시지 반환
        return signupResponseDto;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);             //로그인에 성공하면 response에 jwt를 붙여준다.
        LoginResponseDto loginResponseDto = new LoginResponseDto();   //로그인 성공메시지 반환
        return loginResponseDto;
    }
}
