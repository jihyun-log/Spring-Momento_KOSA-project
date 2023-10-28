// 신이수
// 박지현 - 주문 취소 처리 메서드를 추가하였습니다. (해당 메서드에 주석 추가 완료)

package com.momento.controller;

import com.momento.dto.OrderDto;
import com.momento.dto.OrderHistDto;
import com.momento.entity.OrderItem;
import com.momento.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문을 생성하는 POST 메서드
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러가 발생한 경우, 에러 메시지를 생성하고 400 Bad Request 응답을 반환합니다.
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        // 주문을 생성하고 주문 ID를 반환합니다.
        String email = principal.getName();
        Long orderId;
        try {
            orderId = orderService.order(orderDto, email);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    // 주문 목록을 조회하는 GET 메서드
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        // 페이지별 주문 목록을 조회하고 모델에 데이터를 추가하여 Thymeleaf 템플릿으로 전달합니다.
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "thymeleaf/order/orderHist";
    }

    // KakaoPay 결제 페이지로 이동하는 GET 메서드
    @GetMapping("kakaopay/{orderId}")
    public String kakaoPay(Model model, OrderItem orderItem, Principal principal) {
        // KakaoPay 결제 페이지로 이동하고 주문 아이템 정보를 전달합니다.
        OrderItem realorderItem = orderItem;
        model.addAttribute("orderItem", realorderItem);

        return "thymeleaf/order/kakaopay";
    }



    // 박지현 - 주문 취소 처리 메서드
    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId , Principal principal){


        // - 주문 취소 권한을 확인합니다.
        if(!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
            // - 주문 취소 권한이 없을 경우, HTTP FORBIDDEN 응답코드 (403) 과 함께 에러 메세지를 반환합니다.
        }

        // - 주문 취소 권한이 있을 경우, 주문 취소를 수행합니다.
        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
        // - 주문 취소가 성공할 경우, HTTP OK 응답코드 (200) 과 함께 취소된 주문 ID를 반환합니다.
    }


}