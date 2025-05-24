package org.example.qpin.domain.scrap.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class deleteScrapResDto {

    private Long parkingId;
    private String message;

}