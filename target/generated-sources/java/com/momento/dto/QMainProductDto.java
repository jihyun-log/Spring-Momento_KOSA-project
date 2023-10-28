package com.momento.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.momento.dto.QMainProductDto is a Querydsl Projection type for MainProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainProductDto extends ConstructorExpression<MainProductDto> {

    private static final long serialVersionUID = 799790980L;

    public QMainProductDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<com.momento.constant.ProductSellStatus> productSellStatus, com.querydsl.core.types.Expression<com.momento.constant.ProductTheme> productTheme) {
        super(MainProductDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, com.momento.constant.ProductSellStatus.class, com.momento.constant.ProductTheme.class}, id, title, description, imgUrl, price, productSellStatus, productTheme);
    }

}

