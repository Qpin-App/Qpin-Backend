package org.example.qpin.global.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    /*
        200번대 : 성공 관련
    */
    SUCCESS(200, true, "요청에 성공하였습니다."),
    NOTFOUND(400, false, "요청값이 잘못됐습니다."),
    FAILED(500, false, "서버에러. 요청에 실패하였습니다.");

    private int code;
    private boolean inSuccess;
    private String message;

    /*
        해당되는 코드 매핑
        @param code
        @param inSuccess
        @param message

     */
    ResponseCode(int code, boolean inSuccess, String message) {
        this.inSuccess = inSuccess;
        this.code = code;
        this.message = message;
    }
}