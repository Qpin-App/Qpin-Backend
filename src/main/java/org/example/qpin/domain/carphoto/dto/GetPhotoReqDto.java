package org.example.qpin.domain.carphoto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPhotoReqDto {
    @NotNull(message = "memberId는 필수입니다.")
    @Positive(message = "memberId는 양수여야 합니다.")
    private Long memberId;
}