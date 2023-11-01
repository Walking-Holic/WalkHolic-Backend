package com.example.OpenSource.domain.member.dto;


import com.example.OpenSource.domain.member.domain.Authority;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;

    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    private String profile_image;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getName(),
                member.getAuthority(),
                member.getRank(),
                member.getImage().getImageName()
        );
    }
}
