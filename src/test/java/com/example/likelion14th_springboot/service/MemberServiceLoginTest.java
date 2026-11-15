package com.example.likelion14th_springboot.service;

import com.example.likelion14th_springboot.domain.Member;
import com.example.likelion14th_springboot.dto.request.JoinRequestDto;
import com.example.likelion14th_springboot.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceLoginTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("비밀번호가 일치하면 로그인에 성공한다.")
    void login_Success() {
        // Given
        JoinRequestDto requestDto = new JoinRequestDto();
        ReflectionTestUtils.setField(
                requestDto,
                "name",
                "yeeun"
        );
        ReflectionTestUtils.setField(
                requestDto,
                "password",
                "12345678"
        );

        Member member = Member.builder()
                .name("yeeun")
                .password("암호화된 비밀번호")
                .build();

        given(memberRepository.findByName("yeeun"))
                .willReturn(Optional.of(member));

        given(bCryptPasswordEncoder.matches(
                "12345678",
                "암호화된 비밀번호"
        )).willReturn(true);

        // When
        Member result = memberService.login(requestDto);

        // Then
        assertThat(result).isSameAs(member);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 로그인에 실패한다.")
    void login_Fail_WrongPassword() {
        // Given
        JoinRequestDto requestDto = new JoinRequestDto();
        ReflectionTestUtils.setField(
                requestDto,
                "name",
                "yeeun"
        );
        ReflectionTestUtils.setField(
                requestDto,
                "password",
                "wrongPassword"
        );

        Member member = Member.builder()
                .name("yeeun")
                .password("암호화된 비밀번호")
                .build();

        given(memberRepository.findByName("yeeun"))
                .willReturn(Optional.of(member));

        given(bCryptPasswordEncoder.matches(
                "wrongPassword",
                "암호화된 비밀번호"
        )).willReturn(false);

        // When
        Member result = memberService.login(requestDto);

        // Then
        assertThat(result).isNull();
    }
}