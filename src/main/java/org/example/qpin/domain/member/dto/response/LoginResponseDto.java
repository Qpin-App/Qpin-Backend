package org.example.qpin.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Long memberId;
    private String name;
    private String accessToken;
    private String refreshToken;
}
