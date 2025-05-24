package org.example.qpin.domain.carphoto.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePhotoResDto {
    private Long carPhotoId;
    private String message;
}
