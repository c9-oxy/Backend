package com.example.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {

    @NotEmpty(message = "아이디는 필수입니다.")
    @Size(max = 20, message = "아이디는 20자를 초과할 수 없습니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(max = 20, message = "비밀번호는 20자가 최대입니다.")
    private String userPw;

    @NotEmpty(message = "이름은 필수입니다.")
    @Size(max = 40, message = "이름은 40자가 최대입니다.")
    private String userNm;
}
