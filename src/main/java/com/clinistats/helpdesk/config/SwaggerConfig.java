
package com.clinistats.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jenish(jenishshah671993@gmail.com)
 *
 * 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
	
//	public static final String AUTHORIZATION_HEADER = "Authorization";
//	@Bean
//    public SecurityConfiguration securityInfo() {
//        return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", "");
//    }

        @Bean
        public Docket api() {
            // @formatter:off
            //Register the controllers to swagger
            //Also it is configuring the Swagger Docket
            return new Docket(DocumentationType.SWAGGER_2).select()
                    // .apis(RequestHandlerSelectors.any())
//                    .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                     .paths(PathSelectors.any())
                    // .paths(PathSelectors.ant("/swagger2-demo"))
                    .build().apiInfo(getApiInfo());
                    //.securitySchemes(Arrays.asList(apiKey()))
                    
            // @formatter:on
        }
     
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) 
        {
            //enabling swagger-ui part for visual documentation
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    

    /**
	 * @return
	 */
//	private ApiKey  apiKey() {
//		 return new ApiKey("Authorization", AUTHORIZATION_HEADER, "header");
//	}

	private ApiInfo getApiInfo() {

        return new ApiInfoBuilder()
                .title("Swagger API Doc for Appointment APIS")
                .description("ALL apis are there")
                .version("1.0.0")
                .build();
    }
	
	
} 
