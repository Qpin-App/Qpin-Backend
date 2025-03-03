package org.example.qpin.global.common.repository;

import org.example.qpin.domain.carphoto.entity.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarPhotoRepository extends JpaRepository<CarPhoto, Long> {
    List<CarPhoto> findByMember_MemberId(Long memberId);
}
