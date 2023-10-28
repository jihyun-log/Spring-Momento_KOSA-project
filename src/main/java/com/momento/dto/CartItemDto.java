// 김지현
// 상품 상세 페이지에서 장바구니에 담을 상품 아이디를 전달받을 CartItemDto 클래스 형성

package com.momento.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CartItemDto {

    @NotNull(message = "상품 아이디는 필수 입력 값 입니다.")
    private Long productId;

}