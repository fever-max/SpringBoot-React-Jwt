package com.study.back.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.study.back.config.jwt.JwtProperties;
import com.study.back.config.oauth.GoogleUser;
import com.study.back.config.oauth.OAuthUserInfo;
import com.study.back.model.User;
import com.study.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/google")
    public String jwtCreate(@RequestBody Map<String, Object> data, HttpServletResponse response) {
        System.out.println("jwtCreate 실행됨");
        System.out.println("구글 로그인 요청 아이디:" + data.get("email"));
        String userEmail = (String) data.get("email");

        OAuthUserInfo googleUser = new GoogleUser(data);

        User userEntity = userRepository.findByUserEmail(userEmail);

        if (userEntity == null) {
            // 해당 이메일이 없으면 아이디 생성
            User userRequest = User.builder()
                    .username(googleUser.getName())
                    .password(bCryptPasswordEncoder.encode("구글로그인"))
                    .userEmail(googleUser.getEmail())
                    .provider(googleUser.getProvider())
                    .providerId(googleUser.getProviderId())
                    .roles("ROLE_USER")
                    .build();

            userEntity = userRepository.save(userRequest);
        }

        // jwt 생성
        String jwtToken = JWT.create()
                .withSubject(userEntity.getUserEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userEntity.getNo())
                .withClaim("userName", userEntity.getUsername())
                .withClaim("Role", userEntity.getRoles())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

        // 쿠키에 저장
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setDomain("localhost");
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setMaxAge(30 * 60); // 유효 시간 설정 (30분)
        cookie.setHttpOnly(true); // http에서 수정 불가 (JavaScript를 통해 쿠키에 접근 불가)
        response.addCookie(cookie); // 응답에 쿠키 추가

        return JwtProperties.TOKEN_PREFIX + jwtToken;
    }

}
