package com.example.demo.security.configuration;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 线程不安全
 *
 * RequestMatcher URL请求匹配器
 */
public class SkipPathRequestMatcher implements RequestMatcher {
    private List<String> pathsToSkip;
    private String processingPath;

    public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        this.pathsToSkip = pathsToSkip;
        this.processingPath = processingPath;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(pathsToSkip)) {
            return true;
        }
        List<RequestMatcher> ants = pathsToSkip
                .stream()
                .map(path -> new AntPathRequestMatcher(path))
                .collect(Collectors.toList());
        //or组合多个RequestMatcher 匹配到路径则返回false
        if (new OrRequestMatcher(ants).matches(request)) {
            return false;
        }
        //使用ant风格的路径匹配模板匹配请求
        return new AntPathRequestMatcher(processingPath)
                .matches(request) ? true : false;
    }
}
