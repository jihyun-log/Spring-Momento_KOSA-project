// 신이수
// 박지현 - 주문 취소 메서드를 추가하였습니다. (해당 메서드에 주석 추가 완료)
package com.momento.entity;

import com.momento.constant.OrderStatus;
import com.momento.constant.ProductSellStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    // 주문 상태가 업데이트될 때 호출되는 메서드
    @PreUpdate
    private void onOrderStatusUpdated() {
        if (this.orderStatus == OrderStatus.COMPLETED) {
            // 주문 상태가 COMPLETED일 때 상품 상태를 SOLD_OUT으로 변경
            for (OrderItem orderItem : orderItems) {
                Product product = orderItem.getProduct();
                if (product != null) {
                    product.setProductSellStatus(ProductSellStatus.SOLD_OUT);
                }
            }
        }
    }

    // 주문 아이템을 추가하는 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 회원과 주문 아이템 목록을 받아서 주문 객체를 생성하는 정적 메서드
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.COMPLETED);
        return order;
    }

    // 주문의 총 가격을 계산하는 메서드
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 박지현 - 주문 취소 메서드
    public void cancelOrder(){
        // 주문 상태를 CANCEL로 변경합니다.
        this.orderStatus = OrderStatus.CANCEL;

        // 주문에 속한 모든 상품들을 취소 처리합니다.
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }



}