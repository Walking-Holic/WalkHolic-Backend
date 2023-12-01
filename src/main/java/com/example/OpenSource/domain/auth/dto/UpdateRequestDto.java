package com.example.OpenSource.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRequestDto {
    @NotBlank(message = "별명을 입력해주세요.")
    @Size(min = 2, max = 10, message = "별명은 2자 이상, 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 5, message = "이름은 2자 이상, 5자 이하여야 합니다.")
    private String name;
}
