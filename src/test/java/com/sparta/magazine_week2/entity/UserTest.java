//package com.sparta.magazine_week2.entity;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserTest {
//
//    @Nested
//    @DisplayName("회원가입")
//    class CreateUserInfo {
//
//        private String username;
//        private String password;
//        private String nickName;
//
//        @BeforeEach
//        void setUp() {
//            username = "sseioul@naver.com";
//            password = "kp1234";
//            nickName = "key";
//        }
//
//        @Test
//        @DisplayName("정상 케이스")
//        void reateUserInfo_Normal() {
//
//            User user = new User(username, password, nickName);
//
//            assertNull(user.getId());
//            assertEquals(username, user.getUsername());
//            assertEquals(password, user.getPassword());
//            assertEquals(nickName, user.getNickName());
//
//        }
//
//        @Nested
//        @DisplayName("실패 케이스")
//        class FailCases {
//
//            @Nested
//            @DisplayName("username")
//            class username {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//                    username = null;
//
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new User(username, password, nickName);
//                    });
//
//                    assertEquals("아이디를 입력해주세요.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("빈 문자열")
//                void fail2() {
//                    username = "";
//
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new User(username, password, nickName);
//                    });
//
//                    assertEquals("아이디를 입력해주세요.", exception.getMessage());
//                }
//            }
//
//            @Nested
//            @DisplayName("password")
//            class password {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//                    password = null;
//
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new User(username, password, nickName);
//                    });
//
//                    assertEquals("비밀번호를 입력해주세요.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("빈 문자열")
//                void fail2() {
//                    password = "";
//
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new User(username, password, nickName);
//                    });
//
//                    assertEquals("비밀번호를 입력해주세요.", exception.getMessage());
//                }
//            }
//
//            @Nested
//            @DisplayName("nickName")
//            class nickName {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//                    nickName = null;
//
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new User(username, password, nickName);
//                    });
//
//                    assertEquals("닉네임을 입력해주세요.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("빈 문자열")
//                void fail2() {
//                    nickName = "";
//
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new User(username, password, nickName);
//                    });
//
//                    assertEquals("닉네임을 입력해주세요.", exception.getMessage());
//                }
//            }
//
//        }
//
//    }
//
//}