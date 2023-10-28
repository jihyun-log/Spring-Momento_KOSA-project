//정용준: ProductFormDto 클래스에는 데이터 유효성 검사를 위한
//       @NotBlank 및 @NotNull 어노테이션을 사용하고 있으며,
//       이를 통해 필수 필드가 비어있지 않고, 값이 유효한지 확인합니다.
//       또한, 이 클래스는 데이터베이스 엔터티와 ProductFormDto
//       사이의 변환을 위해 ModelMapper 라이브러리를 사용하고 있습니다.
//       createProduct 메서드는 Product 엔티티로 변환하고,
//       of 메서드는 ProductFormDto 객체로 변환하기 위해 사용됩니다.
//       ProductFormDto 클래스는 주로 상품 등록 및 수정 기능을 제공하며,
//       사용자로부터 입력된 데이터를 데이터베이스와 연동하기 위해 사용됩니다.

package com.momento.dto;

import com.momento.constant.ProductSellStatus;
import com.momento.constant.ProductTheme;
import com.momento.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ProductFormDto {

    private Long id;

    @NotBlank(message = "매입상품명은 필수 입력 값입니다.")
    private String b4Title;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String b4Description;

    @NotNull(message = "판매자아이디는 필수 입력 값입니다.")
    private String createdBy;

    @NotNull(message = "매입일은 필수 입력 값입니다.")
    private String b4Date;

    @NotNull(message = "인스타아이디는 필수 입력 값입니다.")
    private String b4InstaId;

    @NotNull(message = "매입가격은 필수 입력 값입니다.")
    private Integer b4Price;

    private Integer stockNumber;

    private String title;

    private String description;

    private String date;

    private Integer price;

//    private String theme; // 필드 타입 변경

    private ProductSellStatus productSellStatus;

    private ProductTheme productTheme;

    private List<ImageDto> imageDtoList = new ArrayList<>();

    private List<Long> imageIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Product createProduct() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        var convertedProduct = modelMapper.map(this, Product.class);
        convertedProduct.setB4Date(LocalDateTime.parse(this.getB4Date(), formatter));
        convertedProduct.setDate(LocalDateTime.parse(this.getDate(), formatter));

        return convertedProduct;
    }

    public static ProductFormDto of(Product product) {
        return modelMapper.map(product, ProductFormDto.class);
    }
}