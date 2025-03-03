package org.example.qpin.domain.parking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParkingSearchReqDto {
    private double latitude;
    private double longtitude;
    private double distance;
    private String regionCode; //지역코드 5자리 문자열

}
