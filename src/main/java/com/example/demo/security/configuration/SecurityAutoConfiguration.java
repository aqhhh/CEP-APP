package com.example.demo.security.configuration;

import com.example.demo.security.filter.JwtAuthenticationFilter;
import com.example.demo.security.security.JwtAuthenticationProvider;
import com.example.demo.security.security.MallGrantedVoter;
import com.example.demo.security.security.RestAccessDeniedHandler;
import com.example.demo.security.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * spring security 配置文件
 */

@Configuration
@EnableConfigurationProperties({SecurityProperties.class})
@EnableWebSecurity
@ComponentScan({"com.example.demo.security"})
public class SecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    //认证失败处理器
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String[] WEB_WHITELIST = {
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/webjars/**",
            "/health",
            "/monitor/**"
    };

    public static final String API_ROOT_URL = "/**";

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(jwtAuthenticationProvider);
    }


    //装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //定义用户信息服务(决策）
    /*
     * 三大投票器：
     * AffirmativeBased：一票通过
     * ConsensusBased：一半以上功能选举通过
     * UnanimousBased：全票通过才可
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        //投票者
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                new MallGrantedVoter(Arrays.asList(securityProperties.getWithOutAuthorization()),
                        Arrays.asList(securityProperties.getWithOutPermission())),
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);  //投票器
    }

    /*
     * springboot2.0以上版本需要
    //解决AuthenticationManager无法注入导致启动报错的问题
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
     */


    //安全拦截机制
    //WebSecurity主要是配置跟web资源相关的，比如css、js、images等等
    @Override
    public void configure(WebSecurity web) throws Exception {
        //免登录
        /*
         * web ignore比较适合配置前端相关的静态资源，它是完全绕过spring security的所有filter的；
         * 而permitAll，会给没有登录的用户适配一个AnonymousAuthenticationToken，设置到SecurityContextHolder，方便后面的filter可以统一处理authentication。
         */
        web.ignoring().antMatchers(HttpMethod.GET, WEB_WHITELIST);
    }

    //安全拦截机制
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .accessDeniedHandler(this.accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, API_ROOT_URL).permitAll()
                .antMatchers(HttpMethod.GET, WEB_WHITELIST).permitAll()
                .antMatchers(securityProperties.getWithOutAuthorization()).permitAll()
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
                .and()
                .addFilterAt(buildJwtTokenAuthenticationProcessingFilter(authWhiteList(), API_ROOT_URL), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

        //getWithOutPermision里的请求必须认证通过
        if (securityProperties.getWithOutPermission() != null && securityProperties.getWithOutPermission().length > 0) {
            httpSecurity.authorizeRequests()
                    .antMatchers(securityProperties.getWithOutPermission()).access("authenticated");
        }
    }

    protected JwtAuthenticationFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        JwtAuthenticationFilter filter
                = new JwtAuthenticationFilter(authenticationFailureHandler, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    //获取认证白名单 （不需要认证的url)
    private List<String> authWhiteList() {
        String[] custom = securityProperties.getWithOutAuthorization();
        if (custom == null || custom.length == 0) {
            return null;
        }
        return Arrays.asList(custom)
                .stream()
                .filter(x -> !StringUtils.isEmpty(x))
                .collect(Collectors.toList());
    }
}
