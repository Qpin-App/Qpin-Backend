package org.example.qpin.domain.carphoto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarPhotoResponseDto {
    private Long carPhotoId;
    private String carPhotoUrl;
    private String parkingArea;
}
