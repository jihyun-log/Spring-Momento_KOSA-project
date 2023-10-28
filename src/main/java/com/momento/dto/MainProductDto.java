//정용준: MainProductDto 클래스는 상품 목록 화면에서 상품 정보를
//       표시하고, 데이터베이스로부터 조회된 데이터를 클라이언트로 전달하는
//       데 사용됩니다. 이러한 데이터 전송 객체를 사용하면 필요한 데이터만
//       효율적으로 전달할 수 있으며, 엔티티 클래스와 클라이언트 간의
//       데이터 전달을 관리할 수 있습니다.

package com.momento.dto;

import com.momento.constant.ProductSellStatus;
import com.momento.constant.ProductTheme;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainProductDto {

    private Long id;

    private String title;

    private String description;

    private String imgUrl;

    private Integer price;

    private ProductSellStatus productSellStatus;

    private ProductTheme productTheme;

    @QueryProjection
    public MainProductDto(Long id, String title, String description, String imgUrl, Integer price,
                          ProductSellStatus productSellStatus, ProductTheme productTheme){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.productSellStatus = productSellStatus;
        this.productTheme = productTheme;
    }

}