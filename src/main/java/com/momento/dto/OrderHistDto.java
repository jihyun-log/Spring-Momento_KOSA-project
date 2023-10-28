// 신이수
package com.momento.dto;

import com.momento.constant.OrderStatus;
import com.momento.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {
    // 주문 엔티티를 기반으로 생성되는 DTO 클래스
    public OrderHistDto(Order order) {
        // 주문의 정보를 추출하여 DTO에 설정
        this.orderId = order.getOrderId();
        this.b4Date = order.getB4Date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private Long orderId;              // 주문 ID
    private String b4Date;             // 주문 날짜 및 시간
    private OrderStatus orderStatus;   // 주문 상태

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    // 주문 아이템 DTO를 추가하는 메서드
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
