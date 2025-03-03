package org.example.qpin.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberEditInfoResponseDto {

    private String imageUrl;
    private String name;
    private String email;
    private String phoneNumber;
    private Long insuranceId;
}
