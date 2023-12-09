package com.example.OpenSource.domain.trail.service;

import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_DTO;
import static com.example.OpenSource.global.error.ErrorCode.PATH_NOT_FOUND;

import com.example.OpenSource.domain.comment.dto.PathCommentResponseDto;
import com.example.OpenSource.domain.comment.repository.CommentRepository;
import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.trail.domain.Direction;
import com.example.OpenSource.domain.trail.domain.Location;
import com.example.OpenSource.domain.trail.domain.Trail;
import com.example.OpenSource.domain.trail.dto.TrailDetailResponseDto;
import com.example.OpenSource.domain.trail.dto.TrailMainResponseDto;
import com.example.OpenSource.domain.trail.repository.TrailRepository;
import com.example.OpenSource.domain.trail.util.GeometryUtil;
import com.example.OpenSource.global.error.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrailService {
    private final EntityManager em;
    private final TrailRepository trailRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<TrailMainResponseDto> listMyMap(Long memberId, Double x, Double y, Double distance) {
        List<Trail> trails;
        List<TrailMainResponseDto> dtos = new ArrayList<>();

        // Location 자료형으로 변수를 선언하여 해당 요청받은 x,y 값으로 북동쪽과 남서쪽의 위치를 계산
        Location northEast = GeometryUtil.calculate(x, y, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(x, y, distance, Direction.SOUTHWEST.getBearing());

        // 이를 바탕으로 NativeQuery로 북동쪽, 남서쪽 거리를 String으로 작성
        String pointFormat = String.format(
                "'LINESTRING(%f %f, %f %f)'",
                northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude()
        );

        // 범위 내 Trail 찾기
        // NativeQuery로 작성한 pointFormat을 적용
        Query queryTail = em.createNativeQuery(
                "SELECT * " +
                        "FROM trail AS t " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + "), " +
                        "POINT(t.cours_spot_la, t.cours_spot_lo))"
                , Trail.class
        ).setMaxResults(10);

        trails = queryTail.getResultList();

        // 중복 위도 경도 제거 및 가장 빠른 ID의 trail만 선택
        Map<String, Trail> uniqueCoordinatesToTrail = new HashMap<>();
        for (Trail trail : trails) {
            String coordinatesKey = trail.getCoursSpotLa() + "," + trail.getCoursSpotLo();

            // 중복된 좌표가 이미 추가되었는지 확인하고, 가장 빠른 ID를 가진 trail로 갱신
            if (!uniqueCoordinatesToTrail.containsKey(coordinatesKey) ||
                    trail.getId() < uniqueCoordinatesToTrail.get(coordinatesKey).getId()) {
                uniqueCoordinatesToTrail.put(coordinatesKey, trail);
            }
        }

        // 선택된 trails를 dtos에 추가
        for (Trail trail : uniqueCoordinatesToTrail.values()) {
            TrailMainResponseDto response = Optional.of(trail)
                    .map(t -> new TrailMainResponseDto(trail))
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            dtos.add(response);
        }

        // 범위 내 Path 찾기
        Query queryPath = em.createNativeQuery(
                "SELECT DISTINCT p.* " +
                        "FROM path AS p " +
                        "JOIN coordinate AS c ON p.path_id = c.path_id " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + "), POINT(c.latitude, c.longitude))"
                        + "AND c.sequence = 1"
                , Path.class
        );

        List<Path> paths = queryPath.getResultList();
        Set<Long> foundPathIds = new HashSet<>();

        // 중복 경로 제거
        List<Path> filteredPaths = paths.stream()
                .filter(path -> foundPathIds.add(path.getId()))
                .toList();

        for (Path path : filteredPaths) {
            TrailMainResponseDto response = Optional.of(path)
                    .map(t -> new TrailMainResponseDto(path))
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            dtos.add(response);
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public TrailDetailResponseDto trailDetailDto(Long trailId) {
        Trail trail = trailRepository.findById(trailId).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));

        List<PathCommentResponseDto> commentDtoList = commentRepository.findByTrailId(trailId)
                .stream()
                .map(PathCommentResponseDto::new)
                .toList();

        return new TrailDetailResponseDto(trail, commentDtoList);
    }
}
