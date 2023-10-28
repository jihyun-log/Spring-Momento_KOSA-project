//정용준: 이 열거형을 사용하면 코드에서 이러한 상태를 명확하게 참조할 수 있으며,
//       오타나 잘못된 값의 사용을 방지할 수 있습니다.
//신이수: SELL, SOLD_OUT STATUS 추가,
// Status를 활용해 주문과 장바구니에 넣지 못하게 하는 기능을 넣기 위해 추가했습니다

package com.momento.constant;

public enum ProductSellStatus {
    SELL,
    PENDING,
    SOLD_OUT
}
