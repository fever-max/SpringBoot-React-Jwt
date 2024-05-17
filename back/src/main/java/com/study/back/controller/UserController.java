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

import com.study.back.config.auth.PrincipalDetails;
import com.study.back.model.User;
import com.study.back.repository.UserRepository;
import com.study.back.service.UserService;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/logoutOk")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("/user")
    public ResponseEntity<String> user(Authentication authentication) {
        printPrincipalDetails(authentication);
        return ResponseEntity.ok("인가된 유저");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin(Authentication authentication) {
        printPrincipalDetails(authentication);
        return ResponseEntity.ok("인가된 어드민");
    }

    private void printPrincipalDetails(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal.getNo : " + principal.getUser().getNo());
        System.out.println("principal.getUserEmail : " + principal.getUser().getUserEmail());
        System.out.println("principal.getPassword : " + principal.getUser().getPassword());
        System.out.println("principal.getRoles : " + principal.getUser().getRoles());
    }

}
