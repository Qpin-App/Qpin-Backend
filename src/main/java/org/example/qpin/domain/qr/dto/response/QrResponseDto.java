package org.example.qpin.domain.qr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrResponseDto {

    private String safePhoneNumber;

    private String memo;
}
