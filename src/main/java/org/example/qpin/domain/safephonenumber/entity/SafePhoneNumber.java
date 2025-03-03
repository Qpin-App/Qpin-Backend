package org.example.qpin.domain.safephonenumber.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.qr.entity.Qr;
import org.example.qpin.global.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SafePhoneNumber extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long safePhoneNumberId;

    @Column(length = 50, nullable = false)
    private String safePhoneNumber;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
