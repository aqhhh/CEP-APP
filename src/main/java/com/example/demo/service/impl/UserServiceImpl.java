package com.example.demo.service.impl;

import com.example.demo.security.service.AuthRedisService;
import com.example.demo.service.UserService;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthRedisService authRedisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String uploadImg(MultipartFile file) throws IOException {
        /*
         * dataurl方式 相当于是直接把图片存到数据库
         */
        String encode = "data:image/png;base64," + Base64.encode(file.getBytes());
        System.out.println(encode);
        return encode;
    }
}
