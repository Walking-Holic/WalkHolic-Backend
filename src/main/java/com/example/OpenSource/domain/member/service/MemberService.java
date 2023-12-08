package com.example.OpenSource.domain.member.service;

import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;

import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.member.dto.UpdateRankResponse;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDto::of) //toDto
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    public MemberResponseDto findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    @Transactional
    public UpdateRankResponse updateRank(Long memberId, MemberResponseDto dto) {
        boolean isUpdated = false;
        Member oldMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        oldMember.setTime(oldMember.getTime() + dto.getTime());
        oldMember.setWalk(oldMember.getWalk() + dto.getWalk());

        // Rank 계산 및 업데이트
        int totalScore = oldMember.getTime() / 100 + oldMember.getWalk() / 10;
        Rank rank = calculateRank(totalScore);

        if (oldMember.getRank() != rank) {
            isUpdated = true;
        }

        oldMember.setRank(rank);

        // 칼로리 계산
        int caloriesBurned = calculateCalories(dto.getWalk());
        UpdateRankResponse response = new UpdateRankResponse(LocalDate.now(), dto.getWalk(), dto.getTime(),
                caloriesBurned, isUpdated);

        memberRepository.save(oldMember);
        return response;
    }

    // 누적 값에 따라 랭크 계산
    private Rank calculateRank(int totalScore) {
        // 각 랭크의 경계값 설정
        int rank1Boundary = 5000;
        int rank2Boundary = 10000;
        int rank3Boundary = 30000;
        int rank4Boundary = 70000;

        // 랭크 계산
        if (totalScore < rank1Boundary) {
            return Rank.BRONZE;
        } else if (totalScore < rank2Boundary) {
            return Rank.SILVER;
        } else if (totalScore < rank3Boundary) {
            return Rank.GOLD;
        } else if (totalScore < rank4Boundary) {
            return Rank.PLATINUM;
        } else {
            return Rank.DIAMOND;
        }
    }

    // 10000보당 칼로리 계산
    private int calculateCalories(int walkCount) {
        // 1보당 0.03 칼로리라고 가정
        double caloriesPerStep = 0.03;
        return (int) (walkCount * caloriesPerStep);
    }
}
