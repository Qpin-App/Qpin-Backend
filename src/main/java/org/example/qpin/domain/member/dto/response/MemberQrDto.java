package org.example.qpin.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberQrDto {

    private byte[] qrImage;

    private String memo;

    private String safePhoneNumber;

    private LocalDate localDate;
}
