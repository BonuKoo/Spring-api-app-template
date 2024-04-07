package com.app.api.tokentest;

import com.app.domain.member.constant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/token-test")
@RequiredArgsConstructor
public class TokenTestController {

    private final TokenManager tokenManager;

    @GetMapping("/create")
    public JwtTokenDto createJwtTokenDto(){
        return tokenManager.createJwtTokenDto(1L, Role.ADMIN);
    }

    @GetMapping("/valid")
    public String  validateJwtTokenDto(@RequestParam String token){
        tokenManager.validateToken(token);
        Claims tokenCliams = tokenManager.getTokenCliams(token);
       Long memberId = Long.valueOf((Integer) tokenCliams.get("memberId")); //먼저 memberId를 꺼내서 확인
       String role = (String)tokenCliams.get("role");                       //role도
       log.info("memberId : {}",  memberId);
       log.info("role : {}", role);
       return "success";
    }



}
