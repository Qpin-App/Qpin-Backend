package org.example.qpin.domain.qr.controller;

import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.qpin.domain.qr.dto.request.CreateQrRequestDto;
import org.example.qpin.domain.qr.dto.request.ModifyQrRequestDto;
import org.example.qpin.domain.qr.dto.response.CheckQrDto;
import org.example.qpin.domain.qr.dto.response.QrResponseDto;
import org.example.qpin.domain.qr.entity.Qr;
import org.example.qpin.domain.qr.service.QrService;
import org.example.qpin.domain.safephonenumber.entity.SafePhoneNumber;
import org.example.qpin.domain.safephonenumber.service.SafePhoneNumberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QrController {

    private final SafePhoneNumberService safePhoneNumberService;
    private final QrService qrService;

    @PostMapping("/qr/create")
    @Operation(summary = "QR코드 생성", description = "QR코드 생성")
    ResponseEntity<String> createQr(@RequestBody CreateQrRequestDto request) throws WriterException, IOException {

        SafePhoneNumber safePhoneNumber = safePhoneNumberService.findByPhoneNum(request.getPhoneNum());
        Qr qr = qrService.createQr(safePhoneNumber,request);

        return ResponseEntity.status(HttpStatus.OK).body("QR 생성 완료");
    }

    @GetMapping("/qr/{qrId}")
    @Operation(summary = "QR코드를 핸드폰으로 찍었을때", description = "QR코드를 실제로 핸드폰으로 찍었을 때 연결되는 웹페이지")
    ResponseEntity<QrResponseDto>captureQrcode(@PathVariable("qrId") Long qrId){

        Qr qr = qrService.findById(qrId);

        QrResponseDto response = new QrResponseDto(qr.getSafePhoneNumber().getSafePhoneNumber(),qr.getMemo());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/qr/select/{qrId}")
    @Operation(summary = "QR코드 단건 조회", description = "QR코드 단건 조회")
    ResponseEntity<CheckQrDto> checkQr(@PathVariable("qrId")Long qrId){

        CheckQrDto response = qrService.toCheckQrDto(qrId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/qr/selectList/{memberId}")
    @Operation(summary = "QR코드 리스트 조회", description = "멤버의 모든 QR코드 조회")
    ResponseEntity<List<CheckQrDto>> checkQrList(@PathVariable("memberId")Long memberId){

        List<CheckQrDto> response = qrService.toCheckQrDtoList(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/qr/remove/{qrId}")
    @Operation(summary = "QR코드 삭제", description = "QR코드 삭제")
    ResponseEntity<String> removeQr(@PathVariable("qrId") Long qrId){

        qrService.removeQr(qrId);
        return ResponseEntity.status(HttpStatus.OK).body("QR코드 삭제 완료");
    }

    @PutMapping("qr/modify/{qrId}")
    @Operation(summary = "QR코드 수정", description = "QR코드 수정")
    ResponseEntity<String> modifyQr(@PathVariable("qrId") Long qrId,@RequestBody ModifyQrRequestDto request){


        qrService.modifyQr(qrId,request);
        return ResponseEntity.status(HttpStatus.OK).body("QR코드 수정 완료");
    }



}
