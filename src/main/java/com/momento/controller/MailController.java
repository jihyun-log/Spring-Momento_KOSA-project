
// 박지현 - MailController 클래스를 구현하였습니다.
// MailController 클래스는 메일 발송 관련 요청을 처리하는 컨트롤러입니다.
// sendMail 메서드는 MailDto와 첨부 파일을 받아서 메일을 발송될 수 있도록합니다.
// MailController 는 메일 발송에 대한 요청을 처리하고 해당 경로에 대한 뷰 (메일 작성 / 메일 전송 완료) 를 반환합니다.

package com.momento.controller;

import com.momento.dto.MailDto;
import com.momento.dto.ProductSearchDto;
import com.momento.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    // 메일 전송 페이지를 렌더링하기 위한 컨트롤러 메소드입니다.
    @GetMapping(value = "/admin/textMail")
    public String mailSend(ProductSearchDto productSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "thymeleaf/mail/sendemail";      // "thymeleaf/mail/sendemail" 뷰 템플릿을 렌더링합니다.
    }

    @PostMapping("/mail/send")
    public String sendMail(MailDto mailDto, MultipartFile file) throws MessagingException, IOException {
        // MultipartFile: 업로드된 파일을 나타내는 클래스입니다.
        // MailService: 이메일을 보내기 위한 서비스 클래스입니다.

        // mailService를 사용하여 이메일 전송 메서드를 호출합니다.
        mailService.sendMultipleMessage(mailDto, file);

        // 이메일 전송 완료 시, 콘솔에 메세지를 출력합니다.
        System.out.println("메일 전송 완료");
        return "thymeleaf/mail/result";         // "thymeleaf/mail/result" 뷰 템플릿을 렌더링합니다.
    }



}