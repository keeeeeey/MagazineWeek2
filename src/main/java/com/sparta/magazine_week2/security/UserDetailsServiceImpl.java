package com.sparta.magazine_week2.security;

import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
import com.sparta.magazine_week2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR));

        return new UserDetailsImpl(user);
    }
}
