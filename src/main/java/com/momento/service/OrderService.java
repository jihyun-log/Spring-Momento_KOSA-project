//김지현 : 장바구니 상품 주문 로직 추가
// 박지현 - 주문 취소 메서드를 추가하였습니다. (해당 메서드에 주석 표시)

package com.momento.service;

import com.momento.dto.OrderDto;
import com.momento.dto.OrderHistDto;
import com.momento.dto.OrderItemDto;
import com.momento.entity.*;
import com.momento.repository.ImageRepository;
import com.momento.repository.MemberRepository;
import com.momento.repository.OrderRepository;
import com.momento.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ImageRepository imageRepository;

    // 주문 생성 메서드
    public Long order(OrderDto orderDto, String email) {
        // 주문할 상품을 조회합니다.
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(EntityNotFoundException::new);

        // 주문을 생성할 회원을 조회합니다.
        Member member = memberRepository.findByEmail(email);

        // 주문 아이템 리스트를 생성하고 주문을 생성합니다.
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(product);
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getOrderId();
    }

    // 주문 목록 조회 메서드
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                // 주문 아이템에 대한 이미지 정보를 가져와서 OrderItemDto를 생성합니다.
                Image image = imageRepository.findByProductIdAndRepimgYn(orderItem.getProduct().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, image.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<>(orderHistDtos, pageable, totalCount);
    }

    // 주문 취소 권한 검사 메서드
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        // 현재 회원 정보를 조회합니다.
        Member curMember = memberRepository.findByEmail(email);

        // 주문을 조회하고 해당 주문을 생성한 회원 정보를 가져옵니다.
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        // 현재 회원과 주문을 생성한 회원이 동일한지 확인하고 권한을 반환합니다.
        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }


    // 박지현 - 주문 취소 메서드
    public void cancelOrder(Long orderId) {

        // 주문 식별자로 주문을 검색합니다.
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // 주문 취소 메서드를 호출합니다.
        order.cancelOrder();
    }


    //장바구니에서 주문할 상품 데이터를 전달받아서 주문을 생성
    public Long orders(List<OrderDto> orderDtoList, String email) {

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {     //주문할 상품 리스트 생성

            Product product = productRepository.findById(orderDto.getProductId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(product);
            orderItemList.add(orderItem);
        }

        //현재 로그인한 회원과 주문 상품 목록을 이용하여 주문 엔티티를 생성
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        //주문 데이터를 저장
        return order.getOrderId();
    }
}