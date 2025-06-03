package org.example.qpin.domain.qr.dto.request;


import lombok.Builder;
import lombok.Getter;
import org.example.qpin.domain.qr.entity.MyColor;
import org.example.qpin.domain.qr.entity.Sticker;
import org.example.qpin.domain.safephonenumber.entity.SafePhoneNumber;

@Builder
@Getter
public class CreateQrRequestDto {
    private Long memberId;  // 임시

    private String safePhoneNum;

    private String phoneNum;

    private String memo;

    private MyColor myColor;

    private Sticker sticker;

    private String gradation;

    private String backgroundPicture;
}
