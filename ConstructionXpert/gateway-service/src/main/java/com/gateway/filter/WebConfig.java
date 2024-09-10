package com.gateway.filter;//package com.gateway.filter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class MyConfiguration implements WebMvcConfigurer {
//
////  @Override
////  public void addCorsMappings(CorsRegistry registry) {
////    registry.addMapping("/**")
////            .allowedOrigins("http://localhost:64688")
////            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
////            .allowedHeaders("*")
////            .allowCredentials(true);
////  }
//
//  @Bean
//  public CorsWebFilter corsWebFilter() {
//    CorsConfiguration corsConfig = new CorsConfiguration();
//    corsConfig.setAllowedOrigins(List.of("http://localhost:64688"));
//    corsConfig.setMaxAge(3600L);
//    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//    corsConfig.addAllowedHeader("*");
//    corsConfig.setAllowCredentials(true);
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", corsConfig);
//
//    return new CorsWebFilter(source);
//  }
//}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//  @Bean
//  public CorsWebFilter corsWebFilter() {
//    CorsConfiguration corsConfig = new CorsConfiguration();
//    corsConfig.setAllowedOrigins(List.of("http://localhost:64688"));
//    corsConfig.setMaxAge(3600L);
//    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//    corsConfig.addAllowedHeader("*");
//    corsConfig.setAllowCredentials(true);
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", corsConfig);
//
//    return new CorsWebFilter(source);
//  }
    public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:64688")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
  }
}