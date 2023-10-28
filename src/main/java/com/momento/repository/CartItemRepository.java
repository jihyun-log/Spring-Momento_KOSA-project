package com.momento.repository;

import com.momento.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import com.momento.dto.CartDetailDto;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndProductId(Long cartId, Long productId);

    // 장바구니에 있는 상품의 대표 이미지만 가지고 오도록 조건문 작성
    @Query("select new com.momento.dto.CartDetailDto(ci.id, p.title, p.price, im.imgUrl) " +
            "from CartItem ci, Image im " +
            "join ci.product p " +
            "where ci.cart.id = :cartId " +
            "and im.product.id = ci.product.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.b4Date desc"
    )

    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
