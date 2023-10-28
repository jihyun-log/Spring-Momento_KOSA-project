//정용준: 이 코드는 Spring Framework를 사용하여 상품 관리와 관련된
//       웹 애플리케이션의 컨트롤러(ProductController)를 정의하는
//       부분입니다. 이 컨트롤러는 상품 관리 및 관련 웹 페이지에 대한
//       요청을 처리하고 해당 경로에 대한 뷰를 반환합니다.

package com.momento.controller;

import com.momento.dto.ProductFormDto;
import com.momento.dto.ProductSearchDto;
import com.momento.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/user/product/new")
    public String b4Product(Model model) {
        model.addAttribute("productFormDto", new ProductFormDto());
        return "thymeleaf/product/b4ProductForm";
    }

    @GetMapping(value = "/admin/product/new")
    public String product(Model model) {
        model.addAttribute("productFormDto", new ProductFormDto());
        return "thymeleaf/product/b4ProductForm";
    }


    @PostMapping("/user/product/new")
    public String b4Product(@Valid ProductFormDto productFormDto, BindingResult bindingResult,
                            Model model, @RequestParam("imageFile") List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            return "thymeleaf/product/b4ProductForm";
        }

        if (imageFileList.get(0).isEmpty() && productFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        try {
            productService.saveProduct(productFormDto, imageFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        return "redirect:/";
    }


    @PostMapping("/admin/product/new")
    public String product(@Valid ProductFormDto productFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("imageFile") List<MultipartFile> imageFileList) {

        if (bindingResult.hasErrors()) {
            return "thymeleaf/product/b4ProductForm";
        }

        if (imageFileList.get(0).isEmpty() && productFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "thymeleaf/b4ProductForm";
        }

        try {
            productService.saveProduct(productFormDto, imageFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        return "redirect:/admin/products";
    }


    @GetMapping(value = "/user/product/{productId}")
    public String b4Product(@PathVariable("productId") Long productId, Model model) {
        try {
            ProductFormDto productFormDto = productService.getProductDtl(productId);
            model.addAttribute("productFormDto", productFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("productFormDto", new ProductFormDto());
        }

        return "thymeleaf/product/b4ProductForm";
    }

    @GetMapping(value = "/admin/product/{productId}")
    public String product(@PathVariable("productId") Long productId, Model model) {
        try {
            ProductFormDto productFormDto = productService.getProductDtl(productId);
            model.addAttribute("productFormDto", productFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("productFormDto", new ProductFormDto());
        }

        return "thymeleaf/product/ProductForm";
    }

    @PostMapping(value = "/user/product/{productId}")
    public String b4ProductUpdate(@Valid ProductFormDto productFormDto, BindingResult bindingResult,
                                  @RequestParam("imageFile") List<MultipartFile> imageFileList, Model model){
        if (bindingResult.hasErrors()){
            return "thymeleaf/product/b4ProductForm";
        }

        if (imageFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        try {
            productService.updateProduct(productFormDto, imageFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/admin/product/{productId}")
    public String productUpdate(@Valid ProductFormDto productFormDto, BindingResult bindingResult,
                                @RequestParam("imageFile") List<MultipartFile> imageFileList, Model model){
        if (bindingResult.hasErrors()){
            return "thymeleaf/product/b4ProductForm";
        }

        if (imageFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        try {
            productService.updateProduct(productFormDto, imageFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "thymeleaf/product/b4ProductForm";
        }

        return "redirect:/admin/products";
    }


    @GetMapping(value = {"/admin/products", "/admin/products/{page}"})
    public String productManage(ProductSearchDto productSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        var pageable = PageRequest.of(page.orElse(0), 10);
        var products = productService.getAdminProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/product/productMng";
    }

    @GetMapping(value = "/product/{productId}")
    public String itemDtl(Model model, @PathVariable("productId") Long itemId){
        ProductFormDto productFormDto = productService.getProductDtl(itemId);
        model.addAttribute("product", productFormDto);
        return "thymeleaf/product/productDtl";
    }

}