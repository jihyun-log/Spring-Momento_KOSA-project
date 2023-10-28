//정용준: 여기에서 정의된 메서드를 사용하여 데이터베이스에서 Image 엔티티에 대한 CRUD
//       (생성, 조회, 갱신, 삭제) 작업을 수행할 수 있습니다. JpaRepository를 확장하므로
//       별도의 SQL 쿼리를 작성하지 않아도 간단한 메서드 정의를 통해 데이터베이스 액세스를
//       처리할 수 있습니다.

package com.momento.repository;

import com.momento.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductIdOrderById(Long productId);

    Image findByProductIdAndRepimgYn(Long itemId, String repimgYn);
}
