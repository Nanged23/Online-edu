package com.atguigu.staservice.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan({"com.atguigu"})
@EnableSwagger2
public class CorsConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("swagger")
                .description("本文档描述了课程中心微服务接口定义")
                .version("1.0")
                // .contact(new Contact("java", "http://atguigu.com", "1123@qq.com"))
                .build();
    }
    //@Bean
    //public static FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
    //    CorsFilter corsFilter = new CorsFilter(corsFilter());
    //    FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<CorsFilter>(corsFilter);
    //    registration.setOrder(0);
    //    return registration;
    //}
    //@Bean
    //public static CorsConfigurationSource corsFilter() {
    //    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //    CorsConfiguration config = new CorsConfiguration();
    //    config.setAllowCredentials(true);
    //    // config.addAllowedOrigin("*");
    //    config.addAllowedOrigin("http://localhost:9528"); //
    //    config.addAllowedHeader("*");
    //    config.addAllowedMethod("*");
    //    source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
    //    return source;
    //}

    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**") // 所有接口
    //            .allowCredentials(true) // 是否发送 Cookie
    //            .allowedOrigins("http://localhost:9001", "http://localhost:8003", "http://localhost:9528") //
    //            .allowedMethods("GET", "POST", "PUT", "DELETE") // 支持方法
    //            .allowedHeaders("*");
    //}
}
//@Configuration
//@ComponentScan({"com.atguigu.service_vod"})
//public class CorsConfig{
//
//}
