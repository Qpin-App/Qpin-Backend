package org.example.qpin.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {

    private String username;
    private String email;
    private String password;
    private String passwordCheck;
    private String phoneNumber;
}
