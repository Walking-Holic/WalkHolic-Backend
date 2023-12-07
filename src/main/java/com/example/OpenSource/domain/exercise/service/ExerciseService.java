package com.example.OpenSource.domain.exercise.service;

import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_DTO;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import com.example.OpenSource.domain.exercise.dto.ExerciseDto;
import com.example.OpenSource.domain.exercise.repository.ExerciseRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public void save(Long memberId, ExerciseDto exerciseDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Exercise exercise = exerciseDto.toEntity(member);

        exerciseRepository.save(exercise);
    }

    @Transactional(readOnly = true)
    public List<ExerciseDto> getWeeklyExerciseData(Long memberId, LocalDate startDate, LocalDate endDate) {
        List<Exercise> exercises = exerciseRepository.findByMemberIdAndDateBetween(memberId, startDate, endDate);
        List<ExerciseDto> dtos = new ArrayList<>();
        for (Exercise exercise : exercises) {
            ExerciseDto response = Optional.of(exercise)
                    .map(ExerciseDto::of)
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            dtos.add(response);
        }
        return dtos;
    }
}
