package org.example.qpin.domain.scrap.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.domain.parking.entity.Parking;
import org.example.qpin.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Scrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @Column(name = "parking_id")
    private String parkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
