package org.example.qpin.domain.safephonenumber.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.safephonenumber.entity.SafePhoneNumber;
import org.example.qpin.global.common.repository.SafePhoneNumberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SafePhoneNumberService {

    private final SafePhoneNumberRepository safePhoneNumberRepository;

    @Transactional
    public SafePhoneNumber findByPhoneNum(String phoneNum) {
        return safePhoneNumberRepository.findByPhoneNum(phoneNum);
    }
}
