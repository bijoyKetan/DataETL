package service.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.controller.AdminController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/v1/*"))
                .apis(RequestHandlerSelectors.basePackage("service.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "ETL Project API",
                "API specifications for a simple Java ETL project",
                AdminController.getVersion().getBody().toString(),
                "",
                new Contact("Raqeebul Ketan", "", "bijoy.ketan@gmail.com"),
                "",
                "",
                Collections.EMPTY_LIST);
    }
}
