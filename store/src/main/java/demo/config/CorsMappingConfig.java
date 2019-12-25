package demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题
 */
@Configuration
public class CorsMappingConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String mapping = "/**"; // 所有请求，也可配置成特定请求，如/api/**
        String origins = "*"; // 所有来源，也可以配置成特定的来源才允许跨域，如http://www.xxxx.com
        String methods = "*"; // 所有方法，GET、POST、PUT等
        registry.addMapping(mapping).allowedOrigins(origins).allowedMethods(methods);
    }
}
