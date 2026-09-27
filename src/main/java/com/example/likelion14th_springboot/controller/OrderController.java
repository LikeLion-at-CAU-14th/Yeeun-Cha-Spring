package com.example.likelion14th_springboot.controller;

import com.example.likelion14th_springboot.dto.request.OrderCreateRequestDto;
import com.example.likelion14th_springboot.dto.request.OrderUpdateRequestDto;
import com.example.likelion14th_springboot.dto.response.OrderResponseDto;
import com.example.likelion14th_springboot.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderCreateRequestDto dto
    ) {
        return ResponseEntity.ok(
                orderService.createOrder(dto)
        );
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderResponseDto>>
    getOrdersByBuyer(
            @PathVariable Long buyerId
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByBuyer(buyerId)
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
        );
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(
                orderService.updateOrder(orderId, dto)
        );
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long orderId
    ) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.ok(
                "주문이 성공적으로 삭제되었습니다."
        );
    }

}