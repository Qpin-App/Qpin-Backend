package org.example.qpin.domain.carphoto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavePhotoReqDto {

    @NotNull(message = "userId는 필수 입력값입니다.")
    @Positive(message = "userId는 양수여야 합니다.")
    private Long userId;

    @NotNull(message = "parkingAreaId는 필수 입력값입니다.")
    @Positive(message = "parkingAreaId는 양수여야 합니다.")
    private Long parkingAreaId;

    @NotBlank(message = "carPhotoUrl은 필수 입력값입니다.")
    private String carPhotoUrl;
}