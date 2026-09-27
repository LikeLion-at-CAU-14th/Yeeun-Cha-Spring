package com.example.likelion14th_springboot.dto.request;

import com.example.likelion14th_springboot.domain.ShippingAddress;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequestDto {

    private Long buyerId;
    private String recipient;
    private String phoneNumber;
    private String roadAddress;
    private String detailAddress;
    private String zipCode;

    private List<OrderProductRequestDto> products;

    public ShippingAddress toShippingAddress() {
        return ShippingAddress.builder()
                .recipient(recipient)
                .phoneNumber(phoneNumber)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .zipCode(zipCode)
                .build();
    }
}