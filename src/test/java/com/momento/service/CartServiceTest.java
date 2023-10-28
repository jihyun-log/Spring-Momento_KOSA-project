package com.momento.service;

import com.momento.constant.ProductTheme;
import com.momento.dto.CartItemDto;
import com.momento.entity.Product;
import com.momento.entity.CartItem;
import com.momento.entity.Member;
import com.momento.repository.ProductRepository;
import com.momento.repository.CartItemRepository;
import com.momento.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    public Product saveProduct(){
        Product product = new Product();
        product.setTitle("테스트 상품");
        product.setDescription("테스트 상품 상세 설명");
        product.setDate(LocalDateTime.now());
        product.setPrice(10000);
        product.setProductTheme(ProductTheme.ANIME);
        return productRepository.save(product);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Product product = saveProduct();
        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(product.getId());

        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(product.getId(), cartItem.getProduct().getId());
    }

}