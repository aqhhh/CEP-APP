package com.example.demo.config;

import com.example.demo.common.base.BaseResponse;
import com.example.demo.common.enums.ResultCodeBase;
import com.example.demo.common.exception.BaseException;
import com.example.demo.common.exception.UserNotFoundException;
import com.example.demo.security.exception.NeedAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ExceptionHandler
 * @Description 全局异常拦截配置
 * @Author fast
 * @Date 2020/3/15 11:31
 * @Version 1.0
 */
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionHandler {

    @Value("${mall.debug:false}")
    private boolean isDebug;

    @org.springframework.web.bind.annotation.ExceptionHandler(NeedAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public BaseResponse handleNeedAuthenticationException(NeedAuthenticationException needAuthenticationException) {
        log.warn(needAuthenticationException.getMessage(), needAuthenticationException);
        return new BaseResponse(ResultCodeBase.NOT_LOGIN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseResponse handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        log.warn(userNotFoundException.getMessage(), userNotFoundException);
        if (isDebug) {
            return new BaseResponse(ResultCodeBase.USER_NOT_EXIT, userNotFoundException.getMessage(), null);
        }
        return new BaseResponse(ResultCodeBase.USER_NOT_EXIT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleRuntimeException(RuntimeException runtimeException) {
        log.error(runtimeException.getMessage(), runtimeException);
        return new BaseResponse(ResultCodeBase.RUNTIME_EXCEPTION);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public BaseResponse setUserException(BaseException exception) {
        log.info(exception.getReturnMsg());
        BaseResponse responseBase = new BaseResponse(exception.getReturnCode(), exception.getReturnMsg());
        return responseBase;
    }

}
