package com.subutai.customer.result;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseData {

    private int code;
    private String message;
    private Object data;

    @Builder
    public ResponseData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
