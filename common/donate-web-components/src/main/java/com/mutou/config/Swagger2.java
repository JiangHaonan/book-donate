package com.mutou.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${spring.application.name}")
    private String applicationName;

//    http://localhost:8088/swagger-ui.html     原路径
//    http://localhost:8088/doc.html     原路径

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {

        List<Parameter> params = new ArrayList();
        ParameterBuilder userId = new ParameterBuilder();
        userId.name("user-id")
                .description("user-id in login response header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();

        params.add(userId.build());

        ParameterBuilder authorization = new ParameterBuilder();
        authorization.name("Authorization")
                .description("Authorization in login response header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();

        params.add(authorization.build());

        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为swagger2
                    .apiInfo(apiInfo())                 // 用于定义api文档汇总信息
                    .select()
                    .apis(RequestHandlerSelectors
                            .withClassAnnotation(Api.class) // 指定加了@Api注解的类
//                                  .basePackage("com.mutou.controller")   // 指定controller包(不适用)
                    )
                    .paths(PathSelectors.any())         // 所有controller
                    .build().globalOperationParameters(params);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName + "接口api")        // 文档页标题
                .contact(new Contact("",
                        "",
                        ""))        // 联系人信息
                .description("book-donate的api文档")  // 详细信息
                .version("1.0.0")   // 文档版本号
                .termsOfServiceUrl("") // 网站地址
                .build();
    }

}
