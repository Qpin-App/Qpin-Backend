package org.example.qpin.domain.parking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSearchResDto {

    private double longtitude; //경도
    private double latitude; //위도
    private String address; //도로명주소
    private String name; //주차장명
    private String price; //무료,유료
    private Long parkId;
    private double parkingDistance;

    private String weekStartTime;
    private String weekEndTime;
    private String SaturdayStartTime;
    private String SaturdayEndTime;
    private String HolidayStartTime;
    private String HolidayEndTime;

}
