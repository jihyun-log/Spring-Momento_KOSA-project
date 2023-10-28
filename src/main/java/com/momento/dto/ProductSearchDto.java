//정용준: 이 클래스는 상품 검색 시 필요한 검색 조건을 저장하고
//       전달하기 위해 사용됩니다. 사용자가 입력한 검색 조건을
//       이 DTO 객체에 설정한 후, 이 객체를 사용하여 데이터베이스에서
//       상품을 검색하거나 필터링할 수 있습니다.
//       이를 통해 사용자는 원하는 상품을 검색하고 필터링할 수 있습니다.

package com.momento.dto;

import com.momento.constant.ProductSellStatus;
import com.momento.constant.ProductTheme;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSearchDto {

    private String searchDateType;

    private ProductSellStatus searchSellStatus;

    private ProductTheme searchTheme;

    private String searchBy;

    private String searchQuery = "";

}