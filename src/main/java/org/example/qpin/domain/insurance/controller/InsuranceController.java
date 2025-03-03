package org.example.qpin.domain.insurance.controller;

import org.example.qpin.domain.insurance.service.InsuranceService;
import org.example.qpin.domain.insurancenumber.dto.InsuranceNumberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping("/parkingAssistance/{memberId}")
    public InsuranceNumberDto getParkingAssistanceNumber(@PathVariable Long memberId) {
        return insuranceService.getInsuranceNumberByTeamName(memberId, "parkingAssistance");
    }

    @GetMapping("/accidentReport/{memberId}")
    public InsuranceNumberDto getAccidentReportNumber(@PathVariable Long memberId) {
        return insuranceService.getInsuranceNumberByTeamName(memberId, "accidentReport");
    }

    @GetMapping("/accidentProcess/{memberId}")
    public InsuranceNumberDto getAccidentProcessNumber(@PathVariable Long memberId) {
        return insuranceService.getInsuranceNumberByTeamName(memberId, "accidentProcess");
    }

    @GetMapping("/contact/{memberId}")
    public InsuranceNumberDto getContactNumber(@PathVariable Long memberId) {
        return insuranceService.getInsuranceNumberByTeamName(memberId, "contact");
    }
}
