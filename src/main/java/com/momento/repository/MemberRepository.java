// 김지현
// 데이터베이스에 저장할 수 있도록 하는 repository 작성

package com.momento.repository;

import com.momento.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원 가입 시 중복된 회원이 있는지 검사하기 위해 이메일로 회원을 검사할 수 있도록 작성
    Member findByEmail(String email);
}
