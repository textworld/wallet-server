package xyz.ruanxy.java.balance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
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
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(buildApiInf())
            .select()
            .apis(RequestHandlerSelectors.basePackage("xyz.ruanxy.java.balance.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
            .title("系统RESTful API文档")
            .contact(new Contact("textworld", "https://mrbird.cc", "852252810@qq.com"))
            .version("1.0")
            .build();
    }
}
