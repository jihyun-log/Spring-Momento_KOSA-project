// 신이수
package com.momento.repository;

import com.momento.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 회원의 이메일 주소를 기준으로 주문 목록을 조회하는 메서드
    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.b4Date desc"
    )
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    // 회원의 이메일 주소를 기준으로 주문 수를 조회하는 메서드
    @Query("select count(o) from Order o " +
            "where o.member.email = :email"
    )
    Long countOrder(@Param("email") String email);
}