package com.example.demo.common.exception;

import com.example.demo.common.enums.BaseEnum;

public class OperationException extends BaseException {

    private static final long serialVersionUID = -6789162045678459712L;

    public OperationException(){
        super();
    }

    public OperationException(BaseEnum code) {
        super(code);
    }
}
