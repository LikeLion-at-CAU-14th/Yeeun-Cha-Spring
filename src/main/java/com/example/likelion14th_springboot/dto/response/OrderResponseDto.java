package com.example.likelion14th_springboot.dto.response;

import com.example.likelion14th_springboot.domain.Orders;
import com.example.likelion14th_springboot.domain.ShippingAddress;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {

    private Long orderId;
    private Long buyerId;
    private String deliverStatus;
    private ShippingAddress shippingAddress;

    public static OrderResponseDto fromEntity(Orders order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .buyerId(order.getBuyer().getId())
                .deliverStatus(order.getDeliverStatus().name())
                .shippingAddress(order.getShippingAddress())
                .build();
    }
}