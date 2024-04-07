package com.app.domain.member.service;

import com.app.domain.member.entity.Member;
import com.app.domain.member.repository.MemberRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member registerMember(Member member){
        return memberRepository.save(member);

    }

    private void validateDuplicateMember(Member member){
       Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
       if (optionalMember.isPresent()){
           throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
       }
    }
    @Transactional(readOnly = true)
    public Optional<Member> findMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true) // 영속성 컨텍스트에서 변경 감지 기능을 사용하지 않겠다
    public Member findMemberByRefreshToken(String refreshToken) {
       Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));    //멤버를 찾을 수 없으면 예외 발생

        //해당 멤버의 리프레쉬 토큰이 만료됐는 지도 검증
        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
        if (tokenExpirationTime.isBefore(LocalDateTime.now())){
            //refresh Token의 만료 시간이 현재 시간보다 더 작으면, 만료가 됐다.
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }

    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_ACCESS_TOKEN_TYPE));
        //멤버가 존재하지 않으면 예외를 발생
        //이 예외의 경우는, BusinessException을 상속받는
        //entityNotFoundException을 만들도록 한다.
        //EntityNotFoundException을 서비스에서 넘긴다.
    }
}
