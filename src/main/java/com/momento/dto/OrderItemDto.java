package com.momento.dto;

import com.momento.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    // 생성자: 주문 아이템 엔티티와 이미지 정보를 받아 DTO를 생성합니다.
    public OrderItemDto(OrderItem orderItem, String image) {
        // 주문 아이템 엔티티로부터 필요한 정보를 추출하여 DTO에 설정합니다.
        this.title = orderItem.getProduct().getTitle(); // 상품 제목
        this.orderPrice = orderItem.getOrderPrice(); // 주문 가격
        this.image = image; // 이미지 URL
    }

    private int orderPrice; // 주문 가격
    private String image;   // 이미지 URL
    private String title;   // 상품 제목
}
