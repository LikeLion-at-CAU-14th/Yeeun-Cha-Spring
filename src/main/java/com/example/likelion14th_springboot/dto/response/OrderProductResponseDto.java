package com.example.likelion14th_springboot.dto.response;

import com.example.likelion14th_springboot.domain.mapping.ProductOrders;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductResponseDto {

    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;

    public static OrderProductResponseDto fromEntity(
            ProductOrders productOrder
    ) {
        return OrderProductResponseDto.builder()
                .productId(
                        productOrder.getProduct().getId()
                )
                .productName(
                        productOrder.getProduct().getName()
                )
                .price(
                        productOrder.getProduct().getPrice()
                )
                .quantity(productOrder.getQuantity())
                .build();
    }
}