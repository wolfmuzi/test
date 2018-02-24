package com.iuling.comm.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }
    //api接口作者相关信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("宋小雄", "http://www.iuling.com", "Sincere@iuling.com");
        ApiInfo apiInfo = new ApiInfoBuilder().license("GPL").title("MJStyle后台管理其他").description("接口文档").contact(contact).version("3.0").build();
        return apiInfo;
    }
}
