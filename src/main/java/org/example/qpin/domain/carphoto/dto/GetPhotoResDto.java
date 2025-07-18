package org.example.qpin.domain.carphoto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPhotoResDto {
    private Long carPhotoId;
    private String carPhotoUrl;   // 사진 URL 또는 경로
    private Long parkingAreaId;
}