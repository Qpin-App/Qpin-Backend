package org.example.qpin.domain.insurancenumber.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.domain.insurance.entity.Insurance;
import org.example.qpin.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InsuranceNumber extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceNumberId;

    @Column(length = 20)
    private String number;

    @Column
    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
}