package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.LoginRequestDto;
import com.sparta.magazine_week2.dto.LoginResponseDto;
import com.sparta.magazine_week2.dto.UserRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입 확인
    @Transactional
    public UserResponseDto checkRegister(UserRequestDto requestDto) {

        UserResponseDto responseDto = new UserResponseDto();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String nickName = requestDto.getNickName();

        //비밀번호 일치하지 않을 때
        if (!password.equals(passwordCheck)){
            responseDto.setResult(false);
            responseDto.setMsg("비밀번호가 일치하지 않습니다.");
            return responseDto;
        }
        //닉네임 틀렸을 때
        if (password.contains(nickName) || password.length() < 4){
            responseDto.setResult(false);
            responseDto.setMsg("닉네임이 4글자 이하거나, 패스워드에 닉네임이 포함되어있습니다.");
            return responseDto;
        }
        // 닉네임이 알맞지 않을 때
        if (nickName.length() < 3 ||  !Pattern.matches("^[a-zA-Z0-9]*$",nickName)){
            responseDto.setResult(false);
            responseDto.setMsg("닉네임이 3글자 이하거나, 형식이 올바르지 않습니다.");
            return responseDto;
        }

        User user = new User(requestDto);
        userRepository.save(user);

        responseDto.setResult(true);
        responseDto.setMsg("회원가입 성공");
        return responseDto;

    }

    //로그인 확인
    @Transactional
    public LoginResponseDto checklogin(LoginRequestDto userLoginDto){
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        LoginResponseDto responseDto = new LoginResponseDto();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        if (!password.equals(user.getPassword())){
            responseDto.setResult(false);
            responseDto.setMsg("아이디가 존재하지 않거나 일치하지 않습니다.");
            return responseDto;
        }
        responseDto.setResult(true);
        responseDto.setMsg("로그인 성공");
        responseDto.setUsername(user.getUsername());
        responseDto.setNickName(user.getNickName());
        return responseDto;
    }
}
