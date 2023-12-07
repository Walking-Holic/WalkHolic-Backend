package com.example.OpenSource.domain.exercise.service;

import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import com.example.OpenSource.domain.exercise.dto.ExerciseDto;
import com.example.OpenSource.domain.exercise.repository.ExerciseRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import java.time.LocalDate;
import java.util.List;
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

        LocalDate exerciseDate = exerciseDto.getDate();

        // 같은 날짜에 해당하는 운동 데이터 확인
        Exercise existingExercise = exerciseRepository.findByMemberIdAndDate(memberId, exerciseDate);

        if (existingExercise != null) {
            // 같은 날짜에 해당하는 데이터가 있다면 업데이트
            existingExercise.update(exerciseDto);
        } else {
            // 같은 날짜에 해당하는 데이터가 없다면 새로운 데이터 추가
            Exercise newExercise = exerciseDto.toEntity(member);
            exerciseRepository.save(newExercise);
        }
    }

    @Transactional(readOnly = true)
    public List<ExerciseDto> getExerciseDataByDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        List<Exercise> exercises = exerciseRepository.findByMemberIdAndDateBetween(memberId, startDate, endDate);
        return exercises.stream()
                .map(ExerciseDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExerciseDto> getWeeklyExerciseData(Long memberId, LocalDate startDate, LocalDate endDate) {
        return getExerciseDataByDateRange(memberId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<ExerciseDto> getMonthlyExerciseData(Long memberId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return getExerciseDataByDateRange(memberId, startDate, endDate);
    }


}
