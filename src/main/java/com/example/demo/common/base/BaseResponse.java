package com.example.demo.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.common.enums.BaseEnum;
import com.example.demo.common.enums.ResultCodeBase;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -7471377233810564146L;

    private String code;
    private String message;
    private T data;
    @JsonIgnore
    private BaseEnum resultEnum;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(BaseEnum resultEnum, T data) {
        this.data = data;
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
        this.message = resultEnum.getDesc();
    }

    public BaseResponse(BaseEnum resultEnum) {
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
        this.message = resultEnum.getDesc();
    }

    public BaseResponse(String message, BaseEnum resultEnum) {
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
        this.message = message;
    }

    public BaseResponse(BaseEnum resultEnum, T data, String message) {
        this.message = message;
        this.data = data;
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
    }

    //========================OK ============================

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>(ResultCodeBase.SUCCESS);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(ResultCodeBase.SUCCESS, data);
    }

    public static <T> BaseResponse<T> ok(T data, String extendMsg) {
        return new BaseResponse<>(ResultCodeBase.SUCCESS, data, extendMsg);
    }


    //========================faild ============================

    public static <T> BaseResponse<T> faild() {
        return new BaseResponse<>(ResultCodeBase.FAIL);
    }

    public static <T> BaseResponse<T> faild(T data) {
        return new BaseResponse<>(ResultCodeBase.FAIL, data);
    }

    public static <T> BaseResponse<T> faild(T data, String extendMsg) {
        return new BaseResponse<>(ResultCodeBase.FAIL, data, extendMsg);
    }

    //========================serverError ============================

    public static <T> BaseResponse<T> serverError() {
        return new BaseResponse<>(ResultCodeBase.SYSTEM_EXCEPTION);
    }

    public static <T> BaseResponse<T> serverError(T data) {
        return new BaseResponse<>(ResultCodeBase.SYSTEM_EXCEPTION, data);
    }

    public static <T> BaseResponse<T> serverError(T data, String extendMsg) {
        return new BaseResponse<>(ResultCodeBase.SYSTEM_EXCEPTION, data, extendMsg);
    }

    //========================invalidParam ============================

    public static <T> BaseResponse<T> invalidParam() {
        return new BaseResponse<>(ResultCodeBase.REQUEST_ARGUMENTS_ILLEGAL);
    }

    public static <T> BaseResponse<T> invalidParam(T data) {
        return new BaseResponse<>(ResultCodeBase.REQUEST_ARGUMENTS_ILLEGAL, data);
    }

    public static <T> BaseResponse<T> invalidParam(T data, String extendMsg) {
        return new BaseResponse<>(ResultCodeBase.REQUEST_ARGUMENTS_ILLEGAL, data, extendMsg);
    }

}
