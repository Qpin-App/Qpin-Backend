package org.example.qpin.domain.parking.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
// 주차장 검색 요청
public class ParkingSearchReqDto {
    private Double latitude; // 위도
    private Double longitude; // 경도
    private Double distance; // 검색 기준 거리
    private String regionCode; // 지역코드 5자리 문자열

}