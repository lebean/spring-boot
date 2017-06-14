package leix.lebean.sweb.common.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger.title}")
    String title;//API文档标题

    @Value("${swagger.description}")
    String description;//API说明

    @Value("${swagger.version}")
    String version;//API版本

    @Value("${swagger.basePackage}")
    String basePackage;//API基础包名

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

    private ApiKey apiKey() {
        //return new ApiKey("Authorization", "api_key", "header");
        return new ApiKey("Authorization", "", "header");             // <<< === Create a Heaader (We are createing header named "Authorization" here)
    }

    private Predicate<String> petstorePaths() {
        return or(
                regex("/user.*"),
                regex("/cities.*")
        );
    }
}
