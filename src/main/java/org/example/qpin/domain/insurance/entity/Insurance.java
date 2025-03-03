package org.example.qpin.domain.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.domain.insurancenumber.entity.InsuranceNumber;
import org.example.qpin.global.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Insurance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuranceId;

    @Column(length = 20, nullable = false)
    @Enumerated(value = STRING)
    private InsuranceCompany insuranceCompany;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<InsuranceNumber> insuranceNumberList = new ArrayList<>();
}