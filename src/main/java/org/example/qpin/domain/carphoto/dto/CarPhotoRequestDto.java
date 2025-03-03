package org.example.qpin.domain.carphoto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPhotoRequestDto {
    private Long userId;
    private String carPhotoUrl;
    private String parkingArea;
}
