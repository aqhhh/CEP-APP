package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description swagger配置
 * @Author fast
 * @Date 2020/2/29 20:38
 * @Version 1.0
 */

@Configuration
@EnableSwagger2  //开启swagger2
public class SwaggerConfig {

    //地址：http://localhost:8081/swagger-ui.html
    //配置swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //RequestHandlerSelectors. 配置扫描接口的方式
                //basePackage 指定扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .build();
    }

    //配置swagger信息的apiInfo
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("记账APP 接口文档")
                .version("1.0")
                .build();
    }

}
