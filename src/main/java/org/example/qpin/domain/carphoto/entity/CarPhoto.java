package org.example.qpin.domain.carphoto.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.global.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CarPhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carPhotoId;

    @Column(nullable = false)
    private String carPhotoUrl;

    @Column(length = 50, nullable = false)
    private String parkingArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}