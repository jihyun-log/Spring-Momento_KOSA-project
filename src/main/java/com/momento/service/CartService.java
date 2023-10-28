//김지현
//장바구니에 상품을 담는 로직 작성

package com.momento.service;

import com.momento.dto.CartItemDto;
import com.momento.entity.Cart;
import com.momento.entity.CartItem;
import com.momento.entity.Product;
import com.momento.entity.Member;
import com.momento.repository.CartItemRepository;
import com.momento.repository.CartRepository;
import com.momento.repository.ProductRepository;
import com.momento.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import com.momento.dto.CartDetailDto;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.util.StringUtils;

import com.momento.dto.CartOrderDto;
import com.momento.dto.OrderDto;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email){

        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(EntityNotFoundException::new); //장바구니에 담을 상품 엔티티 조회
        Member member = memberRepository.findByEmail(email); //현재 로그인한 회원 엔티티를 조회

        Cart cart = cartRepository.findByMemberId(member.getId());  //현재 로그인한 회원의 장바구니 엔티티를 조회
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        } // 상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티 생성

        //현재 상품이 장바구니에 이미 들어가 있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if(savedCartItem != null){
            return savedCartItem.getId(); //이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량만큼 더해주기
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, product); //장바구니 엔티티, 상품 엔티티를 이용하여 CartItem 엔티티를 생성
            cartItemRepository.save(cartItem); //장바구니에 들어갈 상품 저장
            return cartItem.getId();
        }
    }


    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email); // 현재 로그인한 회원의 장바구니 엔티티를 조회

        Cart cart = cartRepository.findByMemberId(member.getId()); //장바구니에 상품을 한 번도 안담았을 경우, 장바구니의 엔티티가 없으므로 빈 리스트를 반환

        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId()); // 장바구니에 담겨있는 상품 정보를 조회

        return cartDetailDtoList;
    }

    // 현재 로그인한 회원과 해당 장바구니 상품을 저장한 회원이 같은지 검사하는 로직 작성
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        Member curMember = memberRepository.findByEmail(email); //현재 로그인한 회원을 조회
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember(); //장바구니 상품을 저장한 회원 조회

        //현재 로그인한 회원과 장바구니 상품을 저장한 회원이 다를 경우 false, 같으면 true qksghks
        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    //삭제 버튼을 클릭할 때 장바구니에 넣어 놓은 상품을 삭제
    //장바구니 상품 전호를 파라미터로 받아서 삭제하는 로직 추가
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(cartItem.getProduct().getId());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

}