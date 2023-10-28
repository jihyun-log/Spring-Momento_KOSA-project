
// 박지현 - MailService 클래스를 구현하였습니다.
// MailService 클래스는 실제 메일 발송 작업을 수행하는 서비스입니다.
// sendMultipleMessage 메서드는 MailDto와 첨부 파일을 이용하여 메일을 생성하고 전송할 수 있도록합니다.
// 설정된 메일 제목, 참조자, 내용, 발신자, 첨부 파일 및 수신자를 설정하고 메일을 전송할 수 있도록합니다.
//  MailService 클래스 실행을 위해 Spring Boot를 사용하여 이메일 서비스를 구축할 수 있는 spring-boot-starter-mail 의존성을 추가하였습니다.

package com.momento.service;

import com.momento.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;           //commons.io 의존성 추가
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


// javax.mail-api 의존성 추가
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendMultipleMessage(MailDto mailDto, MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // MimeMessage: HTML 레이아웃에서 이미지, 일반적인 메일 첨부 파일 및 텍스트 내용을 지원하는 클래스입니다.
        // MimeMessageHelper: MimeMessage를 생성하고 구성하는 데 도움을 주는 클래스입니다.
        // JavaMailSender: JavaMail MimeMessage를 보내기 위한 인터페이스입니다. (의존성 추가)


        // 메일 제목 설정
        helper.setSubject(mailDto.getTitle());

        // 참조자 설정
        if (mailDto.getCcAddress() != null && mailDto.getCcAddress().length > 0) {
            helper.setCc(mailDto.getCcAddress());
        }

        helper.setText(mailDto.getContent(), false);
        helper.setFrom(mailDto.getFrom());

        // 첨부 파일 설정
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 파일 이름을 UTF-8로 인코딩하여 전송합니다.
        helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"), new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));

        // 수신자 설정
        if (mailDto.getAddress() != null && mailDto.getAddress().length > 0) {
            for (String recipient : mailDto.getAddress()) {
                helper.addTo(recipient);
            }
        }

        // 메일 전송
        emailSender.send(message);
        log.info("Mail multiple send complete.");
    }
}