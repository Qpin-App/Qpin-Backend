package org.example.qpin.domain.parking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 주차장 검색 응답
public class ParkingSearchResDto {
    private Double longitude; // 경도
    private Double latitude; // 위도
    private String address; // 도로명주소
    private String name; // 주차장명
    private String price; // 가격
    private Long parkId; //주차장 아이디
    private Double parkingDistance; // 현위치와의 거리

    private String weekStartTime; // 평일 오픈
    private String weekEndTime; // 평일 마감
    private String SaturdayStartTime; // 토요일 오픈
    private String SaturdayEndTime; // 토요일 마감
    private String HolidayStartTime; // 공휴일/일요일 오픈
    private String HolidayEndTime; // 공휴일/일요일 마감

}
