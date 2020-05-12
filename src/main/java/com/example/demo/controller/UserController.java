package com.example.demo.controller;

import com.example.demo.common.base.BaseResponse;
import com.example.demo.security.service.AuthenticationService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UserController
 * @Description 用户管理模块
 * @Author fast
 * @Date 2020/2/23 19:34
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
@Api(tags  = "UserController-用户接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authentication;

    public static List<String> gender = Arrays.asList("男","女","未知");

    @ApiOperation(value = "图片上传", produces = "application/json")
    @PostMapping("/uploadImg")
    public BaseResponse uploadImg(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        //String imageUrl = userService.uploadImg(file);
        //定义文件保存的本地路径
        String localPath = "D://upload//img//";
        //获取文件名
        String filename = file.getOriginalFilename();
        //文件保存路径
        file.transferTo(new File(localPath + filename));

        String url = "http://192.168.137.1/img/" + filename;

        return BaseResponse.ok(url);
    }


}
