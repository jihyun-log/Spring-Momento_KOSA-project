//정용준: ProductRepositoryCustomImpl 클래스는 ProductRepositoryCustom
//       인터페이스를 구현한 사용자 지정 데이터 액세스 구현체입니다. 이 클래스에서는
//       사용자 지정 쿼리 메서드를 정의하고 해당 메서드를 사용하여 Product 엔티티와
//       관련된 검색 및 페이징 작업을 수행합니다.

package com.momento.repository;

import com.momento.dto.ProductSearchDto;
import com.momento.constant.ProductTheme;
import com.momento.dto.*;
import com.momento.entity.Product;
import com.momento.entity.QProduct;
import com.momento.constant.ProductSellStatus;
import com.momento.entity.QImage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ProductSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QProduct.product.productSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression searchThemeEq(ProductTheme searchProductTheme) {
        return searchProductTheme == null ? null : QProduct.product.productTheme.eq(searchProductTheme);
    }

    private BooleanExpression dateAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QProduct.product.date.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("title", searchBy)) {
            return QProduct.product.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QProduct.product.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        var content = queryFactory
                .selectFrom(QProduct.product)
                .where(dateAfter(productSearchDto.getSearchDateType()),
                        searchSellStatusEq(productSearchDto.getSearchSellStatus()),
                        searchThemeEq(productSearchDto.getSearchTheme()),
                        searchByLike(productSearchDto.getSearchBy(),
                                productSearchDto.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QProduct.product)
                .where(dateAfter(productSearchDto.getSearchDateType()),
                        searchSellStatusEq(productSearchDto.getSearchSellStatus()),
                        searchThemeEq(productSearchDto.getSearchTheme()),
                        searchByLike(productSearchDto.getSearchBy(), productSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QProduct.product.title.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        QProduct product = QProduct.product;
        QImage image = QImage.image;

        List<MainProductDto> content = queryFactory
                .select(
                        new QMainProductDto(
                                product.id,
                                product.title,
                                product.description,
                                image.imgUrl,
                                product.price,
                                product.productSellStatus,
                                product.productTheme)
                )
                .from(image)
                .join(image.product, product)
                .where(image.repimgYn.eq("Y"))
                .where(searchThemeEq(productSearchDto.getSearchTheme()))
                .where(titleLike(productSearchDto.getSearchQuery()))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(image)
                .join(image.product, product)
                .where(image.repimgYn.eq("Y"))
                .where(searchThemeEq(productSearchDto.getSearchTheme()))
                .where(titleLike(productSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }
}