package com.example.OpenSource.domain.auth.dto;

import com.example.OpenSource.domain.member.domain.Authority;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "별명을 입력해주세요.")
    @Size(min = 2, max = 10, message = "별명은 2자 이상, 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 5, message = "이름은 2자 이상, 5자 이하여야 합니다.")
    private String name;

    public Member toMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .name(name)
                .walk(0)
                .authority(Authority.ROLE_USER)
                .rank(Rank.BRONZE)
                .build();
    }
}
