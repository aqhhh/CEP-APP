package com.example.demo.security.service;

import com.example.demo.security.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JwtUserFactory {

    private Logger logger = LoggerFactory.getLogger(JwtUserFactory.class);

//    public UserModel create(User user) {
//        UserModel userModel = UserModel.newBuilder()
//                .id(user.getId())
//                .phone(user.getPhone())
//                .name(user.getName())
//                .gender(user.getGender().intValue())
//                .description(user.getDescription())
//                .imageUrl(user.getImageUrl())
//                .day(user.getDay())
//                .money(user.getMoney().intValue())
//                .score(user.getScore())
//                .password(user.getPassword())
//                .build();
//
//        userModel.setAuthorities(listGrantedAuthorities(userModel.getId()));
//        return userModel;
//    }

    private List<GrantedAuthority> listGrantedAuthorities(Long userId) {
        return null;
    }
}
