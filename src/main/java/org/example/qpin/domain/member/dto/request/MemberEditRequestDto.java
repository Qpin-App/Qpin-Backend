package org.example.qpin.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberEditRequestDto {

    private String imageUrl;
    private String name;
    private String email;
    private String phoneNumber;
    private Long insuranceId;
}
