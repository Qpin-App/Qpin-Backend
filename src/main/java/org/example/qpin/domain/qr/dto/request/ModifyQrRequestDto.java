package org.example.qpin.domain.qr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.qpin.domain.qr.entity.MyColor;
import org.example.qpin.domain.qr.entity.Sticker;

@AllArgsConstructor
@Getter
public class ModifyQrRequestDto {

    private String safePhoneNum;

    private String phoneNum;

    private String memo;

    private MyColor myColor;

    private Sticker sticker;

    private String gradation;
}
