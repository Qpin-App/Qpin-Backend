package org.example.qpin.domain.scrap.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class getScrapReqDto {

    @NotNull(message = "memberId는 필수 입력값입니다.")
    @Positive(message = "memberId는 양수여야 합니다.")
    private Long memberId;
}