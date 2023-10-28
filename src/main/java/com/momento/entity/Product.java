//정용준:  Product 엔티티 클래스에는 상품 정보를 업데이트하거나 재고를
//       추가 및 감소하는 메서드가 정의되어 있습니다.
//       이 클래스는 상품 정보를 데이터베이스에 저장하고 관리하기 위해 사용됩니다.
//       엔티티 간의 관계를 표현하고, 재고 수량을 관리하는 등의 기능을 제공합니다.

//신이수: stock, productsellstatus 관련 코드 추가. stock이 0이 되면 productsellstatus 가 sold_out 되도록.

package com.momento.entity;

import com.momento.constant.ProductTheme;
import com.momento.dto.ProductFormDto;
import com.momento.constant.ProductSellStatus;
import com.momento.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;


@Entity
@Table(name = "Products")
@Getter
@Setter
@ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "b4_title")
    private String b4Title;

    @Column(name = "b4_description", length = 2000)
    private String b4Description;

    @Column(name = "b4_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime b4Date;

    @Column(name = "b4_insta_id")
    private String b4InstaId;

    @Column(name = "b4_price")
    private Integer b4Price;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @Column(name = "price")
    private Integer price;

    @Column
    private int stockNumber; //재고수량

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    @Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private ProductTheme productTheme;


    public void updateProduct(ProductFormDto productFormDto){
        this.id = productFormDto.getId();
        this.b4Title = productFormDto.getB4Title();
        this.b4Description = productFormDto.getB4Description();
        this.b4Date = LocalDateTime.parse(productFormDto.getB4Date());
        this.b4InstaId = productFormDto.getB4InstaId();
        this.b4Price = productFormDto.getB4Price();
        this.title = productFormDto.getTitle();
        this.description = productFormDto.getDescription();
        this.date = LocalDateTime.parse(productFormDto.getDate());
        this.price = productFormDto.getPrice();
        this.productTheme = productFormDto.getProductTheme();
        this.stockNumber = productFormDto.getStockNumber();
        this.productSellStatus = productFormDto.getProductSellStatus();
    }
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;

        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: 0)");
        }

        this.stockNumber = restStock;

        // 재고가 0인 경우에만 상품 상태를 "SOLD_OUT"으로 변경
        if (restStock == 0) {
            this.productSellStatus = ProductSellStatus.SOLD_OUT;
        }
    }

    public void addStock(int stockNumber){

        this.stockNumber += stockNumber;
    }

}