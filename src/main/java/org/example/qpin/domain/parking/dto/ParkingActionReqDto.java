package org.example.qpin.domain.parking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingActionReqDto {

    @NotNull(message = "memberId는 필수입니다.")
    @Positive(message = "memberId는 양수여야 합니다.")
    private Long memberId;

    @NotNull(message = "parkingAreaId는 필수입니다.")
    @Positive(message = "parkingAreaId는 양수여야 합니다.")
    private Long parkingAreaId;

    private String type;
}
