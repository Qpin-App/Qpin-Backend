package org.example.qpin.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class MemberInfoResponseDto {

    private String name;

    private List<MemberQrDto> memberQrDtoList;
}
