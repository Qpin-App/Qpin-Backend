package org.example.qpin.domain.parking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Parking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingAreaId;

    @Column(length = 50, nullable = false)
    private String type;
}