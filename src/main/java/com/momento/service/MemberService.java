// 김지현
// 비지니스 로직을 담당

package com.momento.service;

import com.momento.config.auth.PrincipalDetails;
import com.momento.constant.Role;
import com.momento.entity.Member;
import com.momento.entity.QMember;
import com.momento.entity.SocialUser;
import com.momento.repository.MemberRepository;
import com.momento.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.momento.entity.QMember.member;

@Service
@Transactional  // 로직을 처리하다가 에러가 발생하였다면, 변경된 데이터를 로직을 수행하기 이전 상태로 콜백
@RequiredArgsConstructor
// MemberService가 UserDetailsService를 구현
//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailService 타입으로 ioc되어 있는 loadUserByUsername 함수가 실행
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    private SocialUserRepository socialUserRepository;


    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 로그인할 유저의 email을 파라미터로 전달받는다
    // 원래 썼던 PrincipalDetailsService에 있던 UserDetails loadUserByUsername에 합친다
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);
        SocialUser userEntity = socialUserRepository.findByEmail(email);


        if(userEntity != null) {
            return new PrincipalDetails(userEntity);
        } else if (member == null) {
            throw new UsernameNotFoundException(email);

        }
        // UserDetail을 구현하고 있는 User객체를 반환해준다.
        // User 객체를 생성하기 위햐서 생성자로 회원으 이메일, 비밀번호, role를 파라미터로 넘겨준다.
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

    }



}