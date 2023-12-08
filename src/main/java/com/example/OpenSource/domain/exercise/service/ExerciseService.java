package com.example.OpenSource.domain.exercise.service;

import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import com.example.OpenSource.domain.exercise.dto.ExerciseDto;
import com.example.OpenSource.domain.exercise.dto.ExerciseSummaryDto;
import com.example.OpenSource.domain.exercise.repository.ExerciseRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Transactional(readOnly = true)
    public Map<YearMonth, ExerciseSummaryDto> getYearlyExerciseData(Long memberId, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        List<ExerciseDto> yearlyData = getExerciseDataByDateRange(memberId, startDate, endDate);

        // 월별로 종합
        Map<YearMonth, ExerciseSummaryDto> yearlySummary = new HashMap<>();
        for (ExerciseDto exerciseDto : yearlyData) {
            YearMonth key = YearMonth.from(exerciseDto.getDate());
            yearlySummary.merge(key, exerciseDto.toSummaryDto(),
                    ExerciseSummaryDto::merge); // merge를 통해 두 ExerciseDto를 합칩니다.
        }

        return yearlySummary;
    }

//    @Transactional(readOnly = true)
//    public List<ExerciseDto> getAllExerciseData(Long memberId) {
//        List<Exercise> exercises = exerciseRepository.findByMemberId(memberId);
//        ExerciseSummaryDto summaryDto = new ExerciseSummaryDto();
//        for (Exercise exercise : exercises) {
//            ExerciseDto exerciseDto = ExerciseDto.of(exercise);
//            summaryDto = ExerciseSummaryDto.merge(summaryDto, exerciseDto.toSummaryDto());
//        }
//    }
}
