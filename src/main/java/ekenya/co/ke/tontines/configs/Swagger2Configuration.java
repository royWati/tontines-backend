package ekenya.co.ke.tontines.configs;


import com.google.common.collect.Lists;
import springfox.documentation.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;

/**
 * @Author munialo.roy@ekenya.co.ke
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";
    @Bean
    public Docket api() {

//        List<SecurityScheme> schemeList = new ArrayList<>();
//        schemeList.add(new BasicAuth("basicAuth"));
//        schemeList.add(new BasicAuth("Bearer access_token"));
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("ekenya.co.ke.tontines"))
                .paths(PathSelectors.regex("/.*"))

                .build().apiInfo(apiEndPointsInfo())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("CHAMA APPLICATION RESTFUL APIS")
                .description("Tontine application api development")
                .contact(new Contact("Munialo Roy Wati", "ekenya.co.ke", "munialo.roy@ekenya.co.ke"))
                .license("Apache 2.0")
               // .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.3")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
