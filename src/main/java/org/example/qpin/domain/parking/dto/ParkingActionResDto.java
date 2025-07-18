package org.example.qpin.domain.parking.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingActionResDto {
    private Long parkingId;
    private String message;
}