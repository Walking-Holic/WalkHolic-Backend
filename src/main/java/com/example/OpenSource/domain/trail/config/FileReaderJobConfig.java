package com.example.OpenSource.domain.trail.config;

import com.example.OpenSource.domain.trail.dto.TrailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileReaderJobConfig {

    private final CsvReader csvReader;
    private final CsvTrailWriter csvTrailWriter;

    // Job은 여러 Step을 가질 수 있음.
    @Bean
    public Job csvTrailJob(JobRepository jobRepository, Step csvTrailReaderStep) {
        return new JobBuilder("csvTrailJob", jobRepository)
                .start(csvTrailReaderStep)
                .build();
    }

    // csv 파일 읽고 DB에 쓰는 Step
    @Bean
    public Step csvTrailReaderStep(JobRepository jobRepository,
                                   PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("csvTrailReaderStep", jobRepository)
                .<TrailDto, TrailDto>chunk(2000, platformTransactionManager)
                .reader(csvReader.csvScheduleReader())
                .writer(csvTrailWriter)
//                .allowStartIfComplete(true)
                .build();
    }

}
