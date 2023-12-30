package org.gachon.checkmate.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.member.dto.request.EmailPostRequestDto;
import org.gachon.checkmate.domain.member.dto.request.MemberSignUpRequestDto;
import org.gachon.checkmate.domain.member.dto.response.EmailResponseDto;
import org.gachon.checkmate.domain.member.dto.response.MemberSignUpResponseDto;
import org.gachon.checkmate.domain.member.service.MemberService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
