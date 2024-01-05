package org.gachon.checkmate.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gachon.checkmate.domain.member.dto.request.*;
import org.gachon.checkmate.domain.member.dto.response.*;
import org.gachon.checkmate.domain.member.entity.ProfileImageType;
import org.gachon.checkmate.domain.member.entity.RefreshToken;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.RefreshTokenRepository;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.global.config.auth.jwt.JwtProvider;
import org.gachon.checkmate.global.config.mail.MailProvider;
import org.gachon.checkmate.global.error.exception.ConflictException;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.gachon.checkmate.global.error.exception.InvalidValueException;
import org.gachon.checkmate.global.error.exception.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gachon.checkmate.domain.member.entity.RefreshToken.createRefreshToken;
import static org.gachon.checkmate.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final JwtProvider jwtProvider;
    private final MailProvider mailProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
    private static final Random random = new Random();

    public EmailResponseDto sendMail(EmailPostRequestDto emailPostRequestDto) {
        checkDuplicateEmail(emailPostRequestDto.email());
        String authNum = mailProvider.sendMail(emailPostRequestDto.email(), "email");
        return new EmailResponseDto(authNum);
    }

    public MemberSignUpResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        validatePassword(memberSignUpRequestDto.password());
        Long newMemberId = createMember(memberSignUpRequestDto);
        String accessToken = issueNewAccessToken(newMemberId);
        String refreshToken = issueNewRefreshToken(newMemberId);
        saveTokenInfo(newMemberId, refreshToken);
        return MemberSignUpResponseDto.of(newMemberId, memberSignUpRequestDto.name(), accessToken, refreshToken);
    }

    public MemberSignInResponseDto signIn(MemberSignInRequestDto memberSignInRequestDto) {
        User user = getUserFromEmail(memberSignInRequestDto.email());
        validatePassword(memberSignInRequestDto.password(), user.getPassword());
        String accessToken = issueNewAccessToken(user.getId());
        String refreshToken = issueNewRefreshToken(user.getId());
        saveTokenInfo(user.getId(), refreshToken);
        return MemberSignInResponseDto.of(user.getId(), accessToken, refreshToken);
    }

    public void setPassword(PasswordResetRequestDto passwordResetRequestDto) {
        User user = getUserFromEmail(passwordResetRequestDto.email());
        user.setPassword(encodedPassword(passwordResetRequestDto.newPassword()));
    }

    public MypageResponseDto getMypage(Long userId) {
        User user = findByIdOrThrow(userId);
        return MypageResponseDto.of(user.getProfile(),
                user.getName(),
                user.getMajor(),
                user.getGender().getDesc(),
                user.getMbtiType().getDesc()
        );
    }

    private void validatePassword(String password) {
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new InvalidValueException(INVALID_PASSWORD);
        }
    }

    private User findByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private void validatePassword(String enteredPassword, String storedPassword) {
        if (!authenticatePassword(enteredPassword, storedPassword)) {
            throw new UnauthorizedException(NOT_MATCH_PASSWORD);
        }
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(DUPLICATE_EMAIL);
        }
    }

    private String issueNewAccessToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, true);
    }

    private String issueNewRefreshToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, false);
    }

    private Long createMember(MemberSignUpRequestDto memberSignUpRequestDto) {
        ProfileImageType randomProfile = getRandomProfileImage();
        User newUser = User.createUser(
                memberSignUpRequestDto.email(),
                encodedPassword(memberSignUpRequestDto.password()),
                memberSignUpRequestDto.name(),
                memberSignUpRequestDto.school(),
                memberSignUpRequestDto.major(),
                memberSignUpRequestDto.mbtiType(),
                memberSignUpRequestDto.genderType(),
                randomProfile.getImageUrl()
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

    public void saveTokenInfo(Long userId, String refreshToken) {
        refreshTokenRepository.save(createRefreshToken(userId, refreshToken));
    }

    public MemberReissueTokenResponseDto reissue(MemberReissueTokenRequestDto memberReissueTokenRequestDto) throws JsonProcessingException {
        Long userId = Long.valueOf(jwtProvider.decodeJwtPayloadSubject(memberReissueTokenRequestDto.accessToken()));
        validateRefreshToken(memberReissueTokenRequestDto.refreshToken(), userId);
        String accessToken = issueNewAccessToken(userId);
        String refreshToken = issueNewRefreshToken(userId);
        saveTokenInfo(userId, refreshToken);
        return MemberReissueTokenResponseDto.of(accessToken, refreshToken);
    }

    private void validateRefreshToken(String refreshToken, Long userId) {
        try {
            jwtProvider.validateRefreshToken(refreshToken);
            String storedRefreshToken = getRefreshTokenFromRedis(userId);
            jwtProvider.equalsRefreshToken(refreshToken, storedRefreshToken);
        } catch (UnauthorizedException e) {
            signOut(userId);
            throw e;
        }
    }

    private String getRefreshTokenFromRedis(Long userId) {
        RefreshToken storedRefreshToken = refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(REFRESH_TOKEN_NOT_FOUND));
        return storedRefreshToken.getRefreshToken();
    }

    private void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteById(user.getId());
    }

    public void signOut(Long userId) {
        User user = findByIdOrThrow(userId);
        deleteRefreshToken(user);
    }

    private static ProfileImageType getRandomProfileImage() {
        ProfileImageType[] values = ProfileImageType.values();
        int randomIndex = random.nextInt(values.length);
        return values[randomIndex];
    }

}
