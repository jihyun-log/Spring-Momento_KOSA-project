// 김지현
// 회원 가입 화면으로부터 넘어오는 가입정보를 담은 dto 생성

package com.momento.dto;

import lombok.Getter;
import lombok.Setter;

// 유효성을 검증할 클래스의 필드에 어노테이션을 선언
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberFormDto {

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNumber;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotEmpty(message = "인스타 아이디는 필수 입력 값입니다.")
    private String instaId;

}
