package top.leix.restful.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Name:Swagger2Config
 * Description:配制Swagger2
 * Author:leix
 * Time: 2017/3/28 14:02
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_12)
                .apiInfo(apiInfo())
                .select()

                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RESTful API文档")
                .description("项目API文档")
                .version("1.0")
                .build();
    }

    private Predicate<String> petstorePaths() {
        return or(
                regex("/user.*"),
                regex("/cities.*")
        );
    }
}
