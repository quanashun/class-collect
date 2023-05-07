package top.ashun.recruit.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author 18483
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {
    @Value("${swagger.enabled: true}")
    private boolean swaggerEnable;
    @Value("${version: 1.0.0}")
    private String version;

    /**
     * 自定义一个Apikey
     * 这是一个包含在header中键名为Authorization的JWT标识
     */
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
    /**
     * 配置JWT的SecurityContext 并设置全局生效
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }


    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(defaultApiInfo())
                .enable(swaggerEnable)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .select()
                //这是扫描所有接口
                .apis(RequestHandlerSelectors.any())
                //这是只扫描带有@Api注解的类
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //这是扫描带有@ApiOperation注解的方法
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()/*.forCodeGeneration(true)*/;

    }

    private ApiInfo defaultApiInfo() {
        return new ApiInfoBuilder()
                .title("报名系统-接口文档")
                .description("")
                //服务条款网址
                .version(version)
                .build();
    }
}

