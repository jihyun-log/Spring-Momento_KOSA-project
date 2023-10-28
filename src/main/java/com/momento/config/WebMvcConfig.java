//정용준: 이 코드는 /images/로 시작하는 URL에 대한 요청을 uploadPath에 지정된 디렉토리에서 찾아
//해당 리소스를 제공하게 됩니다. 이를 통해 웹 애플리케이션에서 이미지나 기타 정적 리소스를 관리하고
//제공할 수 있게 됩니다.

package com.momento.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}