package com.example.likelion14th_springboot.domain;


import com.example.likelion14th_springboot.domain.enums.DeliverStatus;
import com.example.likelion14th_springboot.domain.mapping.ProductOrders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus; // 배송상태

    @Embedded
    private ShippingAddress shippingAddress; //배송정보

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<ProductOrders> productOrders = new ArrayList<>();

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Coupon coupon;

    public void addProductOrder(ProductOrders productOrder) {
        this.productOrders.add(productOrder);
    }
}
