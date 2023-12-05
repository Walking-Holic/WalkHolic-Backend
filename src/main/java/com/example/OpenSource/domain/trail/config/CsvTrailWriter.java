package com.example.OpenSource.domain.trail.config;

import com.example.OpenSource.domain.trail.domain.Trail;
import com.example.OpenSource.domain.trail.dto.TrailDto;
import com.example.OpenSource.domain.trail.repository.TrailRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvTrailWriter implements ItemWriter<TrailDto> {

    private final TrailRepository trailRepository;

    @Override
    public void write(Chunk<? extends TrailDto> items) throws Exception {
        List<Trail> scheduleList = new ArrayList<>();

        log.info(String.valueOf(items.size()));
        for (TrailDto trailDto : items) {
            Trail schedule = trailDto.toEntity();
            scheduleList.add(schedule);
        }

        trailRepository.saveAll(scheduleList);
    }
}
