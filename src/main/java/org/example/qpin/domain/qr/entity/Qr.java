package org.example.qpin.domain.qr.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.safephonenumber.entity.SafePhoneNumber;
import org.example.qpin.global.common.BaseEntity;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qr extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private MyColor myColor;

    @Enumerated(value = EnumType.STRING)
    private Sticker sticker;

    @Column
    private int gradation; //추후 검토 필요

    @Column
    private String background_picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="safe_phone_number_id")
    private SafePhoneNumber safePhoneNumber;

    @Column
    private String qrUrl;

    @Lob
    @Column(name = "qr_image", columnDefinition = "MEDIUMBLOB")
    @Comment("QR 이미지")
    private byte[] qrImage;

    public Qr(Member member,String memo,MyColor myColor, Sticker sticker, int gradation, SafePhoneNumber safePhoneNumber){
        this.member = member;
        this.memo = memo;
        this.myColor = myColor;
        this.sticker = sticker;
        this.gradation = gradation;
        this.safePhoneNumber = safePhoneNumber;
    }

    public void setQrUrl(String qrUrl){
        this.qrUrl = qrUrl;
    }

    public void setQrImage(byte[] qrImage) {
        this.qrImage = qrImage;
    }

    public void modifyQr(String memo,MyColor myColor, Sticker sticker, int gradation,
                         SafePhoneNumber safePhoneNumber){
        this.memo = memo;
        this.myColor = myColor;
        this.sticker = sticker;
        this.gradation = gradation;
        this.safePhoneNumber = safePhoneNumber;
    }
}
