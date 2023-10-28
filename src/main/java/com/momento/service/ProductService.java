//정용준: ProductService 클래스는 상품 등록, 수정, 조회, 및 페이지네이션을 처리하며,
//       이미지 처리에 대한 로직을 ImageService 클래스를 통해 위임합니다.
//       이 서비스는 상품 관련 비즈니스 로직을 수행합니다.

package com.momento.service;

import com.momento.dto.ImageDto;
import com.momento.dto.MainProductDto;
import com.momento.dto.ProductFormDto;
import com.momento.dto.ProductSearchDto;
import com.momento.entity.Image;
import com.momento.entity.Product;
import com.momento.repository.ImageRepository;
import com.momento.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> imageFileList) throws Exception {

        //상품 등록
        Product product = productFormDto.createProduct();
        productRepository.save(product);

        //이미지 등록
        for (int i = 0; i < imageFileList.size(); i++) {
            Image image = new Image();
            image.setProduct(product);

            if (i == 0)
                image.setRepimgYn("Y");
            else
                image.setRepimgYn("N");

            imageService.saveImage(image, imageFileList.get(i));
        }

        return product.getId();
    }

    @Transactional(readOnly = true)
    public ProductFormDto getProductDtl(Long productId) {
        List<Image> imageList = imageRepository.findByProductIdOrderById(productId);
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : imageList) {
            ImageDto imageDto = ImageDto.of(image);
            imageDtoList.add(imageDto);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDto productFormDto = ProductFormDto.of(product);
        productFormDto.setImageDtoList(imageDtoList);
        return productFormDto;
    }


    public Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> imageFileList) throws Exception {
        //상품 수정
        Product product = productRepository.findById(productFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        product.updateProduct(productFormDto);
        List<Long> imageIds = productFormDto.getImageIds();

        //이미지 등록
        for (int i = 0; i < imageFileList.size(); i++) {
            imageService.updateImage(imageIds.get(i), imageFileList.get(i));
        }

        return product.getId();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        return productRepository.getAdminProductPage(productSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        return productRepository.getMainProductPage(productSearchDto, pageable);
    }
}