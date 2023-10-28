//신이수
package com.momento.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    // 주문할 상품의 ID를 나타내는 필드
    @NotNull
    private Long productId;

}