package org.example.qpin.global.common.repository;

import org.example.qpin.domain.parking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Parking> findParkingByParkingAreaIdAndMember(Long parkingAreaId, Long memberId);
}
