package com.example.likelion14th_springboot.service;

import com.example.likelion14th_springboot.domain.Member;
import com.example.likelion14th_springboot.domain.Orders;
import com.example.likelion14th_springboot.domain.Product;
import com.example.likelion14th_springboot.domain.enums.DeliverStatus;
import com.example.likelion14th_springboot.domain.mapping.ProductOrders;
import com.example.likelion14th_springboot.dto.request.OrderCreateRequestDto;
import com.example.likelion14th_springboot.dto.request.OrderProductRequestDto;
import com.example.likelion14th_springboot.dto.response.OrderResponseDto;
import com.example.likelion14th_springboot.repository.MemberRepository;
import com.example.likelion14th_springboot.repository.OrderRepository;
import com.example.likelion14th_springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDto createOrder(
            OrderCreateRequestDto dto
    ) {
        // 1. 구매자 조회
        Member buyer = memberRepository.findById(dto.getBuyerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "구매자를 찾을 수 없습니다."
                        )
                );

        // 2. 주문 생성
        Orders order = Orders.builder()
                .buyer(buyer)
                .deliverStatus(DeliverStatus.PREPARATION)
                .shippingAddress(dto.toShippingAddress())
                .build();

        // 3. 주문할 상품 조회
        for (OrderProductRequestDto item : dto.getProducts()) {
            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "상품을 찾을 수 없습니다."
                            )
                    );

            // 4. 주문과 상품의 연결 정보 생성
            ProductOrders productOrder =
                    ProductOrders.builder()
                            .orders(order)
                            .product(product)
                            .quantity(item.getQuantity())
                            .build();

            // 5. 주문에 상품 연결 정보 추가
            order.addProductOrder(productOrder);
        }

        // 6. 주문 저장
        Orders savedOrder = orderRepository.save(order);

        // 7. 응답 DTO로 변환
        return OrderResponseDto.fromEntity(savedOrder);
    }
}