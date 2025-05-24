package org.example.qpin.domain.scrap.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class getScrapResDto {
    private Long scrapId;
    private Long parkingId;
}