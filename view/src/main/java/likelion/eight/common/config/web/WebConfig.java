package likelion.eight.common.config.web;

import likelion.eight.interceptor.AuthorizationInterceptor;
import likelion.eight.resolver.UserCookieResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final UserCookieResolver userCookieResolver;


    @Value("${app.upload.url}")
    private String UPLOAD_DIR;


    private List<String> DEFAULT_EXCLUDE = List.of(
            "/",
            "/login",
            "/logout",
            "/home",
            "/health",
            "/favicon.ico",
            "/css/**",
            "/error",
            "/users/create",
            "/boothouse/camps",
            "/courses/*", // /courses/**가 아니고, /courses/* 가 맞아요
            "/bootcamps/**",
            "/images/**"
    );


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(DEFAULT_EXCLUDE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userCookieResolver);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/noticeImages/**")
                .addResourceLocations("file:" + UPLOAD_DIR);
    }
}
