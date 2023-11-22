package com.example.OpenSource.domain.path.service;

import static com.example.OpenSource.global.error.ErrorCode.IMAGE_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_DTO;
import static com.example.OpenSource.global.error.ErrorCode.PATH_NOT_FOUND;

import com.example.OpenSource.domain.comment.dto.CommentResponseDto;
import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.comment.repository.CommentRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.domain.path.domain.Coordinate;
import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.path.dto.CoordinateDto;
import com.example.OpenSource.domain.path.dto.PathAllResponseDto;
import com.example.OpenSource.domain.path.dto.PathDetailResponseDto;
import com.example.OpenSource.domain.path.dto.PathRequestDto;
import com.example.OpenSource.domain.path.repository.CoordinateRepository;
import com.example.OpenSource.domain.path.repository.PathRepository;
import com.example.OpenSource.global.error.CustomException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.sql.rowset.serial.SerialBlob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class PathService {
    private final PathRepository pathRepository;
    private final MemberRepository memberRepository;
    private final CoordinateRepository coordinateRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Boolean addPath(PathRequestDto pathRequestDto, Long memberId, MultipartFile pathImage) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Path newPath = pathRequestDto.toPath(member); //toEntity

        if (pathImage != null && !pathImage.isEmpty()) {
            savePathImageFromDto(pathImage, newPath);
        } else {
            throw new CustomException(IMAGE_NOT_FOUND);
        }

        List<Coordinate> coordinatesList = pathRequestDto.getCoordinates().stream()
                .map(coordinateDto -> mapCoordinateDtoToEntity(coordinateDto, newPath))
                .collect(Collectors.toList());

        pathRepository.save(newPath);
        coordinateRepository.saveAll(coordinatesList);

        return true;
    }

    private void savePathImageFromDto(MultipartFile pathImage, Path newPath) {
        try {
            Blob blob = new SerialBlob(pathImage.getBytes());
            newPath.setPathImage(blob);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Coordinate mapCoordinateDtoToEntity(CoordinateDto coordinateDto, Path path) {
        return Coordinate.builder()
                .path(path)
                .sequence(coordinateDto.getSequence())
                .latitude(coordinateDto.getLatitude())
                .longitude(coordinateDto.getLongitude())
                .build();
    }

    public List<PathAllResponseDto> getAllPaths() {
        List<Path> paths = pathRepository.findAll();
        List<PathAllResponseDto> pathAllDtos = new ArrayList<>();

        for (Path path : paths) {
            PathAllResponseDto response = Optional.of(path).map(PathAllResponseDto::new)
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            pathAllDtos.add(response);
        }

        return pathAllDtos;
    }

    /*
    TODO: 해당 게시판의 댓글도 출력하도록 수정하기
     */
    public PathDetailResponseDto getPathResponseById(Long pathId) {
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));

        List<CommentResponseDto> commentDtoList = commentRepository.findByPathId(pathId)
                .stream()
                .map(Comment::toCommentResponseDto)
                .collect(Collectors.toList());

        return PathDetailResponseDto.of(path, commentDtoList);
    }

    /*
    TODO: 수정, 삭제 기능 추가하기
     */
}
