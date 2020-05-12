package com.example.demo.security.security;

import com.example.demo.security.model.MallGrantedAuthority;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MallGrantedVoter implements AccessDecisionVoter<Object> {

    private final List<String> whiteList;

    private Logger logger = LoggerFactory.getLogger(MallGrantedVoter.class);

    public MallGrantedVoter(List<String> withOutAuthorization, List<String> withOutPermission) {
        whiteList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(withOutAuthorization)) {
            logger.info("装载允许匿名访问url到MallGrantedVoter白名单...");
            whiteList.addAll(withOutAuthorization);
            System.out.println(whiteList);
        }
        if (!CollectionUtils.isEmpty(withOutPermission)) {
            logger.info("装载无需权限访问url到MallGrantedVoter白名单...");
            whiteList.addAll(withOutPermission);
            System.out.println(whiteList);
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        logger.debug("开始APP鉴权流程...");
        if (authentication == null) {
            logger.info("authentication为空, 非法...");
            return ACCESS_DENIED;
        }

        FilterInvocation filterInvocation = (FilterInvocation) object;
        final String contextPath= filterInvocation.getRequest().getContextPath();
        String requestUri =filterInvocation.getRequest().getRequestURI();
        if(StringUtils.isNotBlank(contextPath)){
            requestUri=requestUri.substring(contextPath.length(),requestUri.length());
        }

        final String url = requestUri;
        final String method = filterInvocation.getHttpRequest().getMethod();
        final PathMatcher matcher = new AntPathMatcher();

        boolean flag = whiteList.stream()
                .anyMatch(white -> matcher.match(white, url));
        if (flag) {
            logger.debug("匹配到白名单url, 弃权...");
            return ACCESS_ABSTAIN;
        }

        //获取authentication的权限
        /*
        Collection<MallGrantedAuthority> authorities = findMallGrantedAuthority(authentication.getAuthorities());
        if (CollectionUtils.isEmpty(authorities)) {
            logger.warn("无MallGrantedAuthority, 用户非法...");
            return ACCESS_DENIED;
        }

        return authorities.stream().anyMatch(x -> checkPermission(url, method, x)) ?
                ACCESS_GRANTED : ACCESS_DENIED;
         */
        return ACCESS_GRANTED;  //暂时设置为直接通过
    }

    private boolean checkPermission(String url, String method, MallGrantedAuthority authority) {
        return true;
        /*
        PathMatcher matcher = new AntPathMatcher();

        boolean authUrl = matcher.match(authority.getUrl(), url);
        if (UrlMethod.ALL.name().equals(authority.getMethod())) {
            return authUrl;
        }
        return authUrl && method.equals(authority.getMethod());
         */
    }

    private Collection<MallGrantedAuthority> findMallGrantedAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .filter(x -> x instanceof MallGrantedAuthority)
                .map(x -> (MallGrantedAuthority) x)
                .collect(Collectors.toList());
    }
}
