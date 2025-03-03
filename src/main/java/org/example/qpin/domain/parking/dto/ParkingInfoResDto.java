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

    private boolean parkingStatus;
    private boolean scrapStatus;
    private LocalDateTime parkingDate;
    private int parkingTime;

}
