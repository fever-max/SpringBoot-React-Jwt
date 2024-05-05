package com.study.back.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.back.model.User;
import com.study.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        System.out.println("PrincipalDetailsService 넘어온 email : " + email);
        User user = userRepository.findByUserEmail(email);

        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(user);
    }

}
