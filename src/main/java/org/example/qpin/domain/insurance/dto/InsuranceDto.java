package org.example.qpin.domain.insurance.dto;

import lombok.*;
import org.example.qpin.domain.insurancenumber.dto.InsuranceNumberDto;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDto {
    private Long insuranceId;
    private String insuranceCompany;
    private List<InsuranceNumberDto> insuranceNumberDtoList;
}
