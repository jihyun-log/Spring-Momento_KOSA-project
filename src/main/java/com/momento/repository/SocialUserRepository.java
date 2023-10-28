// 김지현
// 데이터베이스에 저장할 수 있도록 하는 repository 작성

package com.momento.repository;

import com.momento.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    public SocialUser findByUsername(String username);

    public SocialUser findByEmail(String email);

}