package com.example.OpenSource.domain.path.service;

import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_DTO;
import static com.example.OpenSource.global.error.ErrorCode.PATH_NOT_FOUND;

import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.path.dto.PathAllResponseDto;
import com.example.OpenSource.domain.path.repository.PathRepository;
import com.example.OpenSource.global.error.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathCollectionService {
    private final PathRepository pathRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean add(Long memberId, Long pathId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));

        member.getCollectedPaths().add(path);
        pathRepository.save(path);
        for (Path p : member.getPathCollections()) {
            log.info(String.valueOf(p.getId()));
        }
        return true;
    }

    @Transactional
    public boolean remove(Long memberId, Long pathId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));

        member.getCollectedPaths().remove(path);
        pathRepository.save(path);
        for (Path p : member.getPathCollections()) {
            log.info(String.valueOf(p.getId()));
        }
        return true;
    }


    public List<PathAllResponseDto> getMemberCollections(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        List<Path> paths = member.getCollectedPaths();
        List<PathAllResponseDto> pathAllDtos = new ArrayList<>();
        log.info(String.valueOf(paths.size()));
        for (Path path : paths) {
            PathAllResponseDto response = Optional.of(path)
                    .map(p -> new PathAllResponseDto(p, member))
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            pathAllDtos.add(response);
        }

        return pathAllDtos;
    }
}
