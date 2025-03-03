package org.example.qpin.global.common.repository;

import org.example.qpin.domain.safephonenumber.entity.SafePhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafePhoneNumberRepository extends JpaRepository<SafePhoneNumber, Long> {
    SafePhoneNumber findByPhoneNum(String phoneNum);
}
