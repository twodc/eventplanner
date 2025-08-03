package com.example.eventplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EventRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 30)
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 200)
    private String description;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

}
