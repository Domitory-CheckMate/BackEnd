package org.gachon.checkmate.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.member.dto.request.EmailPostRequestDto;
import org.gachon.checkmate.domain.member.dto.request.MemberSignInRequestDto;
import org.gachon.checkmate.domain.member.dto.request.MemberSignUpRequestDto;
import org.gachon.checkmate.domain.member.dto.request.PasswordResetRequestDto;
import org.gachon.checkmate.domain.member.dto.response.EmailResponseDto;
import org.gachon.checkmate.domain.member.dto.response.MemberSignInResponseDto;
import org.gachon.checkmate.domain.member.dto.response.MemberSignUpResponseDto;
import org.gachon.checkmate.domain.member.service.MemberService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/email")
    public ResponseEntity<SuccessResponse<?>> sendMail(@RequestBody final EmailPostRequestDto emailPostRequestDto) {
        final EmailResponseDto emailResponseDto = memberService.sendMail(emailPostRequestDto);
        return SuccessResponse.ok(emailResponseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signUp(@RequestBody final MemberSignUpRequestDto memberSignUpRequestDto){
        final MemberSignUpResponseDto memberSignUpResponseDto = memberService.signUp(memberSignUpRequestDto);
        return SuccessResponse.ok(memberSignUpResponseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestBody final MemberSignInRequestDto memberSignInRequestDto){
        final MemberSignInResponseDto memberSignInResponseDto = memberService.signIn(memberSignInRequestDto);
        return SuccessResponse.ok(memberSignInResponseDto);
    }

    @PatchMapping("/reset")
    public ResponseEntity<SuccessResponse<?>> setPassword(@RequestBody final PasswordResetRequestDto passwordResetRequestDto){
        memberService.setPassword(passwordResetRequestDto);
        return SuccessResponse.ok(null);
    }
}
