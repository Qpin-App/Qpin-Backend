package org.example.qpin.domain.qr.dto.request;


import lombok.Builder;
import lombok.Getter;
import org.example.qpin.domain.qr.entity.MyColor;
import org.example.qpin.domain.qr.entity.Sticker;

@Builder
@Getter
public class CreateQrRequestDto {
    private String safePhoneNum;

    private String phoneNum;

    private String memo;

    private MyColor myColor;

    private Sticker sticker;

    private String gradation;

    private Long memberId;  // 임시
}
