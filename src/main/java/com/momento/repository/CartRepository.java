//김지현
//현재 로그인한 회원의 Cart 엔티티를 찾기 위해서 쿼리 메소드 추가
//카트 아이디와 상품 아이디를 이용해 상품이 장바구니에 들어있는지 조회

package com.momento.repository;

import com.momento.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}