package com.example.demo.common.exception;

import com.example.demo.common.enums.BaseEnum;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 4540164817117468586L;

    private BaseEnum returnCode;

    public BaseException() {
        super();
    }

    public BaseException(BaseEnum code) {
        super(code.getDesc());
        this.returnCode = code;
    }

    public BaseEnum getReturnCode() {
        return returnCode;
    }

    public String getCode() {
        return returnCode.getCode();
    }

    public String getReturnMsg() {
        return returnCode.getDesc();
    }
}
