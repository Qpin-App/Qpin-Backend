package org.example.qpin.domain.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingInfoResDto {

    private LocalDateTime parkingDate;  // 주차 시작 시간
    private int parkingTime;  // 주차한 시간(분 단위)
    private Long parkingAreaId;  // 주차장 ID
    private String parkingType;  // 주차장 유형
}
