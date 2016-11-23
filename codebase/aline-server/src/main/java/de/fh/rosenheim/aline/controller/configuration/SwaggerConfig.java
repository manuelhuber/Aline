package de.fh.rosenheim.aline.controller.configuration;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final TypeResolver typeResolver;
    @Value("${token.header}")
    private String tokenHeader;

    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.fh.rosenheim.aline.controller.rest"))
                .paths(PathSelectors.any())
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        // ResponseEntity<Foo> should be display as Foo
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)),
                        // Iterable<Foo> should be displayed as List<Foo> since Spring Boot automatically serializes
                        // Iterables as List/Array
                        newRule(typeResolver.resolve(Iterable.class, WildcardType.class),
                                typeResolver.resolve(List.class, WildcardType.class)));
    }

    @Bean
    SecurityConfiguration security() {
        // This is copied from the springfox docu. We only need to change the header key
        return new SecurityConfiguration(
                "test-app-client-id",
                "test-app-client-secret",
                "test-app-realm",
                "test-app",
                "",
                ApiKeyVehicle.HEADER,
                tokenHeader,
                "," /*scope separator*/);
    }
}
