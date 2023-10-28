// 김지현
// 회원 가입 후 메인 페이지로 갈 수 있도록 controller 작성

package com.momento.controller;

import com.momento.dto.MemberFormDto;
import com.momento.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momento.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;


@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "thymeleaf/member/memberForm";
    }

    // 회원가입이 성공하면, 메인페이지로 리다이렉트
    // 회원 정보 검증 및 중복회원 가입 조건에 의해 실패한다면 다시 회원 가입 페이지로 돌아가 실패 이유를 화면에 출력
    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "thymeleaf/member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "thymeleaf/member/memberForm";
        }

        return "redirect:/";
    }

    //로그인 페이지로 이동
    @GetMapping(value = "/login")
    public String loginMember(){
        return "thymeleaf/member/memberLoginForm";
    }

    //로그인 실패 시
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "thymeleaf/member/memberLoginForm";
    }

}