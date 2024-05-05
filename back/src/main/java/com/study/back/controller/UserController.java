package com.study.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;

import com.study.back.model.User;
import com.study.back.repository.UserRepository;
import com.study.back.service.UserService;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        System.out.println("회원가입 컨트롤러 실행" + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        System.out.println("비밀번호 인코딩:" + encPassword);
        user.setPassword(encPassword);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return ResponseEntity.ok("회원가입 완료");
    }

}
