package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    /*
     * 图片上传
     */
    String uploadImg(MultipartFile file) throws IOException;

}
