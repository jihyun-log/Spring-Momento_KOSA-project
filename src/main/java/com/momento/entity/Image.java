//정용준: Image 엔티티 클래스는 이미지의 정보를 데이터베이스에 저장하고
//       관리하기 위해 사용됩니다. 이미지의 정보를 업데이트하는 updateImage
//       메서드도 포함되어 있습니다. 이 클래스는 상품과 이미지 간의 관계를
//       표현하며, 이미지 파일의 이름과 URL을 저장하여 이미지 데이터를 관리합니다.

package com.momento.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter @Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String imgName;

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl;

    private String repimgYn; //대표 이미지 여부


    public void updateImage(String oriImgName, String imgName, String imageUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.setImgUrl(imageUrl);
    }

}