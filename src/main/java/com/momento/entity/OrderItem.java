//신이수
// 박지현 - 주문 취소 메서드를 추가하였습니다. (해당 메서드에 주석 추가 완료)

package com.momento.entity;

import com.momento.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OrderItems")
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // 주문 아이템에 해당하는 상품

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;      // 주문 아이템이 속한 주문

    private int orderPrice;   // 주문 가격

    // 주문 아이템을 생성하는 정적 메서드
    public static OrderItem createOrderItem(Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrderPrice(product.getPrice());
        product.removeStock(1); // 상품 재고에서 1개를 빼줌
        return orderItem;
    }

    // 주문 아이템의 총 가격을 반환하는 메서드
    public int getTotalPrice() {
        return orderPrice;
    }


    // 박지현 - 주문 취소 메서드
    // 상품을 취소하면 해당 상품의 재고를 1 증가시킵니다.
    public void cancel() {
        this.getProduct().addStock(1);
    }

}