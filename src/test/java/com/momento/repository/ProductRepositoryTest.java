package com.momento.repository;

import com.momento.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createProductItemTest() {
        Product product = new Product();
        product.setB4Title("테스트 상품");
        product.setB4Description("테스트 상품 상세 설명");
        product.setB4Date(LocalDateTime.now());
        product.setB4InstaId("테스트 인스타 아이디");
        product.setB4Price(10000);
        Product savedProduct = productRepository.save(product);
        System.out.println(savedProduct.toString());
    }

}