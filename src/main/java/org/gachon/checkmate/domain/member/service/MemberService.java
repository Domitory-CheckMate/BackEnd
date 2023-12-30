package org.gachon.checkmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gachon.checkmate.domain.member.dto.request.EmailPostRequestDto;
import org.gachon.checkmate.domain.member.dto.request.MemberSignInRequestDto;
import org.gachon.checkmate.domain.member.dto.request.MemberSignUpRequestDto;
import org.gachon.checkmate.domain.member.dto.response.EmailResponseDto;
import org.gachon.checkmate.domain.member.dto.response.MemberSignInResponseDto;
import org.gachon.checkmate.domain.member.dto.response.MemberSignUpResponseDto;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.global.config.auth.jwt.JwtProvider;
import org.gachon.checkmate.global.config.mail.MailProvider;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.gachon.checkmate.global.error.exception.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gachon.checkmate.global.error.ErrorCode.INVALID_PASSWORD;
import static org.gachon.checkmate.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final JwtProvider jwtProvider;
    private final MailProvider mailProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public EmailResponseDto sendMail(EmailPostRequestDto emailPostRequestDto) {
        String authNum = mailProvider.sendMail(emailPostRequestDto.email(), "email");
        return new EmailResponseDto(authNum);
    }

    public MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        Long newMemberId = createMember(memberSignUpRequestDto);
        String accessToken = issueNewToken(newMemberId);
        String refreshToken = issueNewToken(newMemberId);
        return MemberSignUpResponseDto.of(newMemberId, memberSignUpRequestDto.name(), accessToken, refreshToken);
    }

    public MemberSignInResponseDto signIn(MemberSignInRequestDto memberSignInRequestDto){
        User user = getUserFromEmail(memberSignInRequestDto.email());
        if (!authenticatePassword(memberSignInRequestDto.password(), user.getPassword())) {
            throw new UnauthorizedException(INVALID_PASSWORD);
        }
        String accessToken = issueNewToken(user.getId());
        String refreshToken = issueNewToken(user.getId());
        return MemberSignInResponseDto.of(user.getId(), accessToken, refreshToken);
    }

    private String issueNewToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, true);
    }

    private Long createMember(MemberSignUpRequestDto memberSignUpRequestDto) {
        User newUser = User.createUser(
                memberSignUpRequestDto.email(),
                encodedPassword(memberSignUpRequestDto.password()),
                memberSignUpRequestDto.name(),
                memberSignUpRequestDto.school(),
                memberSignUpRequestDto.major(),
                memberSignUpRequestDto.mbtiType(),
                memberSignUpRequestDto.genderType()
        );
        return userRepository.save(newUser).getId();
    }

    private String encodedPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private boolean authenticatePassword(String enteredPassword, String storedPassword) {
        return passwordEncoder.matches(enteredPassword, storedPassword);
    }

    private User getUserFromEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }


}
