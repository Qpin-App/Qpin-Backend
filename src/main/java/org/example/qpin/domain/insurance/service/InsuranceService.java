package org.example.qpin.domain.insurance.service;

import org.example.qpin.domain.insurance.dto.InsuranceDto;
import org.example.qpin.domain.insurancenumber.dto.InsuranceNumberDto;

public interface InsuranceService {
    InsuranceNumberDto getInsuranceNumberByTeamName(Long userId, String teamName);
}
