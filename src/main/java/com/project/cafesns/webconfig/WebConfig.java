package com.project.cafesns.webconfig;

import com.project.cafesns.jwt.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("localhost:8080")
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(true);
    }

    //이미지 관련
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///home/ubuntu/upload/");
    }

    //토큰을 받아야 하는 서비스 설정
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(jwtTokenInterceptor) //로그인이 필요한 서비스 요청시 Interceptor가 그 요청을 가로챔
                .addPathPatterns("/api/user/signout")
                .addPathPatterns("/api/auth/refresh")
                .addPathPatterns("/api/upload")
                //게시글 작성,수정,삭제
                .addPathPatterns("/{cafeId}/posts", "/posts/{postId}", "/posts/{postId}")
                .addPathPatterns("/admin/**");
    }
}