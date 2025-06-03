package org.example.qpin.domain.qr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.qpin.domain.qr.entity.MyColor;
import org.example.qpin.domain.qr.entity.Sticker;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckQrDto {

    private String safePhoneNumber;

    private String memo;

    private MyColor myColor;

    private Sticker sticker;

    private String gradation; //추후 검토 필요

    private byte[] qrImage;

}
