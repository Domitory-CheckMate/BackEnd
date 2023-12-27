package org.gachon.checkmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gachon.checkmate.domain.member.dto.request.EmailPostRequestDto;
import org.gachon.checkmate.domain.member.dto.response.EmailResponseDto;
import org.gachon.checkmate.global.config.auth.jwt.JwtProvider;
import org.gachon.checkmate.global.config.mail.MailProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final JwtProvider jwtProvider;
    private final MailProvider mailProvider;

    public EmailResponseDto sendMail(EmailPostRequestDto emailPostRequestDto) {
        return mailProvider.sendMail(emailPostRequestDto, "email");
    }

}
