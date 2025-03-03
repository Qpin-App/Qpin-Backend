package org.example.qpin.global.common.repository;

import org.example.qpin.domain.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Optional<Insurance> findById(Long insuranceId);
}
