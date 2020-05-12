package com.example.demo.security.service.impl;

import com.example.demo.security.service.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ksewen
 * @date 2018/11/236:01 PM
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private JwtUserFactory jwtUserFactory;
//
//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        User user = userMapper.selectByPrimaryKey(userId == null ? 0L : Long.parseLong(userId));
//
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("找不到userId对应用户: {}", userId));
//        } else {
//            return jwtUserFactory.create(user);
//        }
//    }
}
