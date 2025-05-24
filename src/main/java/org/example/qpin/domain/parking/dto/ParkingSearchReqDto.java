package org.example.qpin.domain.parking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingSearchReqDto {

    @NotNull(message = "위도는 필수입니다.")
    private Double latitude;

    @NotNull(message = "경도는 필수입니다.")
    private Double longitude;

    @NotNull(message = "반경 정보는 필수입니다.")
    private Double distance;

    @NotNull(message = "지역코드는 필수입니다.")
    private String regionCode;
}