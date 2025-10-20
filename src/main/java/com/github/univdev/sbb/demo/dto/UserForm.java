package com.github.univdev.sbb.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
    private long id;

    @NotBlank(message = "이름은 반드시 입력 되어야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 반드시 입력 되어야 합니다.")
    @Email(message = "이메일 형식으로 입력해야 합니다.")
    private String email;

    @NotBlank(message = "나이는 반드시 입력 되어야 합니다.")
    @Min(value = 10, message = "나이는 10살 이상이여야 합니다.")
    @Max(value = 100, message = "나이는 100살 이하여야 합니다.")
    private int age;
}
