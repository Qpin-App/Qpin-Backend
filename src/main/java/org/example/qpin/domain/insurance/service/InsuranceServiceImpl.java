package org.example.qpin.domain.insurance.service;

import org.example.qpin.domain.insurance.entity.Insurance;

import org.example.qpin.domain.insurancenumber.dto.InsuranceNumberDto;
import org.example.qpin.domain.insurancenumber.entity.InsuranceNumber;
import org.example.qpin.domain.insurancenumber.repository.InsuranceNumberRepository;
import org.example.qpin.domain.member.entity.Member;
import org.example.qpin.global.common.repository.InsuranceRepository;
import org.example.qpin.global.common.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceImpl implements InsuranceService {
    private final MemberRepository memberRepository;
    private final InsuranceRepository insuranceRepository;
    private final InsuranceNumberRepository insuranceNumberRepository;

    @Autowired
    public InsuranceServiceImpl(MemberRepository memberReository, InsuranceRepository insuranceRepository, InsuranceNumberRepository insuranceNumberRepository) {
        this.memberRepository = memberReository;
        this.insuranceRepository = insuranceRepository;
        this.insuranceNumberRepository = insuranceNumberRepository;
    }

    @Override
    public InsuranceNumberDto getInsuranceNumberByTeamName(Long userId, String teamName) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Member not found for id: " + userId));

        Insurance insurance = member.getInsurance();
        InsuranceNumber insuranceNumber = insuranceNumberRepository.findByInsuranceAndTeamName(insurance, teamName)
                .orElseThrow(() -> new RuntimeException("InsuranceNumber not found for insuranceId: " + insurance.getInsuranceId() + " and teamName: " + teamName));

        return InsuranceNumberDto.builder()
                .insuranceNumberId(insuranceNumber.getInsuranceNumberId())
                .number(insuranceNumber.getNumber())
                .teamName(insuranceNumber.getTeamName())
                .build();
    }
}
