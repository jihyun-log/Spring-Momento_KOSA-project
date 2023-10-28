package com.momento.service;

import com.momento.constant.OrderStatus;
import com.momento.dto.OrderDto;
import com.momento.entity.*;
import com.momento.repository.MemberRepository;
import com.momento.repository.OrderRepository;
import com.momento.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    public Product saveProduct(){
        Product product = new Product();
        product.setTitle("테스트 상품");
        product.setPrice(10000);
        product.setDescription("테스트 상품 상세 설명");
        return productRepository.save(product);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);

    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Product product = saveProduct();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();

        Long orderId = orderService.order(orderDto, member.getEmail());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = product.getPrice();


        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
    }

//    @Test
//    @DisplayName("주문 취소 테스트")
//    public void cancelOrder(){
//        Item item = saveItem();
//        Member member = saveMember();
//
//        OrderDto orderDto = new OrderDto();
//        orderDto.setCount(10);
//        orderDto.setItemId(item.getId());
//        Long orderId = orderService.order(orderDto, member.getEmail());
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(EntityNotFoundException::new);
//        orderService.cancelOrder(orderId);
//
//        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
//        assertEquals(100, item.getStockNumber());
//    }

}