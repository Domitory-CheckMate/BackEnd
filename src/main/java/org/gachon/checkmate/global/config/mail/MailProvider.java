package org.gachon.checkmate.global.config.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gachon.checkmate.domain.member.dto.request.EmailPostRequestDto;
import org.gachon.checkmate.global.error.exception.InternalServerException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

import static org.gachon.checkmate.global.error.ErrorCode.EMAIL_SEND_ERROR;

@Slf4j
@RequiredArgsConstructor
@Component
public class MailProvider {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(String email, String type) {
        String authNum = createNumericCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("[CHECKMATE] 이메일 인증번호 발송"); // 메일 제목
            mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문
            javaMailSender.send(mimeMessage);
            return authNum;
        } catch (MessagingException e) {
            throw new InternalServerException(EMAIL_SEND_ERROR);
        }
    }

    private static String createNumericCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

}
