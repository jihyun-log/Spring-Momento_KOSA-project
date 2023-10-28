//정용준: 여기에서 정의된 인터페이스를 사용하여 Product 엔티티와 관련된 다양한 데이터베이스
//       작업을 수행할 수 있습니다. Spring Data JPA 및 QueryDSL을 사용하여 복잡한
//       데이터베이스 액세스 작업을 간단하게 처리할 수 있습니다.

package com.momento.repository;

import com.momento.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {

}
