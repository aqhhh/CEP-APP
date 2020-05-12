package com.example.demo.controller;

import com.example.demo.service.AuthService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AuthController
 * @Description 用户登录模块
 * @Author fast
 * @Date 2020/3/1 21:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/auth")
@Api(tags  = "AuthController-登录接口")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;


}
