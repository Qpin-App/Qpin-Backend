package org.example.qpin.domain.scrap.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class deleteScrapReqDto {

    @NotNull(message = "scrapId는 필수 입력값입니다.")
    @Positive(message = "scrapId는 양수여야 합니다.")
    private Long scrapId;

}
