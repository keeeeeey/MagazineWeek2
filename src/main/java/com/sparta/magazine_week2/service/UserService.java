package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.UserRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.entity.UserRoleEnum;
import com.sparta.magazine_week2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입 확인
    @Transactional
    public UserResponseDto checkRegister(UserRequestDto requestDto) {
        UserResponseDto responseDto = new UserResponseDto();

        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            responseDto.setMsg("중복된 사용자 ID 가 존재합니다.");
            responseDto.setResult(false);
            return responseDto;
        }

        // 패스워드 암호화
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            responseDto.setMsg("비밀번호가 일치하지 않습니다.");
            responseDto.setResult(false);
            return responseDto;
        }
        String bcryptpassword = passwordEncoder.encode(password);
        String nickName = requestDto.getNickName();

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                responseDto.setMsg("관리자 암호가 틀려 등록이 불가능합니다.");
                responseDto.setResult(false);
                return responseDto;
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, bcryptpassword, nickName, role);
        userRepository.save(user);

        responseDto.setMsg("회원가입이 완료 되었습니다.");
        responseDto.setResult(true);
        return responseDto;
    }

}
