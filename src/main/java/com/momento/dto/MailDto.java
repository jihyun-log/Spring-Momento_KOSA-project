
// 박지현 - MailDto 클래스를 구현하였습니다.
// MailDto 클래스는 메일의 속성을 저장하는 클래스입니다.
// 발신자, 수신자, 참조자, 제목 및 내용을 저장하는 데 사용되어집니다.

package com.momento.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {

    private String from;
    private String[] address;
    private String[] ccAddress;
    private String title;
    private String content;
}