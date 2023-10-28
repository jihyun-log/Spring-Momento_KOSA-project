//정용준: ProductRepositoryCustom 인터페이스는 사용자 지정 데이터 액세스 메서드를 정의하는 데 사용됩니다.
//       이 인터페이스를 구현하면 별도의 데이터 액세스 메서드를 제공할 수 있습니다. ProductRepositoryCustom에서
//       정의된 메서드들은 ProductRepository와 함께 사용하여 Product 엔티티에 대한 사용자 지정 쿼리를 실행합니다.

package com.momento.repository;

import com.momento.dto.*;
import com.momento.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable);

    Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);

}