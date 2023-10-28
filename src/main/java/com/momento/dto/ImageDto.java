//정용준: 이 코드는 DTO (Data Transfer Object)
//       패턴을 사용하여 엔티티 클래스 Image의
//       정보를 전달하는 ImageDto 클래스를 정의합니다.
//       이를 통해 엔티티 객체의 필드를 클라이언트에
//       효과적으로 전달할 수 있습니다.

package com.momento.dto;

import com.momento.entity.Image;
import com.momento.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ImageDto {

    private Long id;

    private Product product;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ImageDto of(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }

}