package com.example.OpenSource.domain.member.dto;


import com.example.OpenSource.domain.member.domain.Authority;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.sql.Blob;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String email;

    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    private int walk;
    private int time;

    private byte[] profileImage;

    public static MemberResponseDto of(Member member) {
        MemberResponseDto dto = new MemberResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getName(),
                member.getAuthority(),
                member.getRank(),
                member.getWalk(),
                member.getTime(),
                null  // null 초기화 후, 이미지 Blob -> bytes 작업 후 적용
        );
        dto.declareProfileImage(member.getProfileImage());
        return dto;
    }

    // Blob -> bytes
    public void declareProfileImage(Blob profileImage) {
        if (profileImage != null) {
            try {
                this.profileImage = profileImage.getBytes(1, (int) profileImage.length());
            } catch (SQLException e) {
                // 예외 처리
            }
        }
    }
}
