package org.example.qpin.domain.insurancenumber.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceNumberDto {
    private Long insuranceNumberId;
    private String number;
    private String teamName;
}
