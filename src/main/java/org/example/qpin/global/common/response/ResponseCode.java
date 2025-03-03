package org.example.qpin.global.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    /*
        2000번대 : 성공 관련
    */
    SUCCESS(2000, true, "요청에 성공하였습니다.");

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
