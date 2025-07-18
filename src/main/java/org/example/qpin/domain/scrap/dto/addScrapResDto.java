package org.example.qpin.domain.scrap.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class addScrapResDto {

    private Long scrapId;
    private String message;

}