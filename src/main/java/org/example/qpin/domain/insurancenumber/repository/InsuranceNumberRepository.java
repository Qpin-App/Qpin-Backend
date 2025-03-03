package org.example.qpin.domain.insurancenumber.repository;

import org.example.qpin.domain.insurance.entity.Insurance;
import org.example.qpin.domain.insurancenumber.entity.InsuranceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceNumberRepository extends JpaRepository<InsuranceNumber, Long> {
    Optional<InsuranceNumber> findByInsuranceAndTeamName(Insurance insurance, String teamName);
}
