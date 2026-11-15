package com.example.likelion14th_springboot.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("회원의 예치금을 충전하고 사용할 수 있다.")
    void updateDeposit() {
        // Given
        Member member = Member.builder()
                .name("예은")
                .deposit(1000)
                .build();

        // When: 500원 충전
        member.chargeDeposit(500);

        // Then
        assertThat(member.getDeposit()).isEqualTo(1500);

        // When: 300원 사용
        member.useDeposit(300);

        // Then
        assertThat(member.getDeposit()).isEqualTo(1200);
    }
}