package com.example.OpenSource.domain.trail.service;

import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_DTO;

import com.example.OpenSource.domain.trail.domain.Direction;
import com.example.OpenSource.domain.trail.domain.Location;
import com.example.OpenSource.domain.trail.domain.Trail;
import com.example.OpenSource.domain.trail.dto.TrailMainResponseDto;
import com.example.OpenSource.domain.trail.util.GeometryUtil;
import com.example.OpenSource.global.error.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
public class TrailService {
    private final EntityManager em;

    @Transactional(readOnly = true)
    public List<TrailMainResponseDto> listMyMap(Long memberId, Double x, Double y) {
        List<Trail> trails = new ArrayList<>();
        List<TrailMainResponseDto> dtos = new ArrayList<>();

        // TODO: Path도 출력

        // Location 자료형으로 변수를 선언하여 해당 요청받은 x,y 값으로 북동쪽과 남서쪽의 위치를 계산
        Location northEast = GeometryUtil.calculate(x, y, 2.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(x, y, 2.0, Direction.SOUTHWEST.getBearing());

        // 이를 바탕으로 NativeQuery로 북동쪽, 남서쪽 거리를 String으로 작성
        String pointFormat = String.format(
                "'LINESTRING(%f %f, %f %f)'",
                northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude()
        );

        // NativeQuery로 작성한 pointFormat을 적용
        Query query = em.createNativeQuery(
                "SELECT * " +
                        "FROM trail AS t " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + "), " +
                        "POINT(t.cours_spot_la, t.cours_spot_lo))"
                , Trail.class
        ).setMaxResults(10);
        log.info(query.toString());

        trails = query.getResultList();

        for (Trail trail : trails) {
            TrailMainResponseDto response = Optional.of(trail)
                    .map(t -> new TrailMainResponseDto(trail))
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            dtos.add(response);
        }

        return dtos;
    }
}