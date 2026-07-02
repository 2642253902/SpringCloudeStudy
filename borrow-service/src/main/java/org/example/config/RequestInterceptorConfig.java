package org.example.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@Configuration
public class RequestInterceptorConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // SecurityContextHolder 保存了当前请求的认证信息。
        // 也就是说，用户访问 borrow-service 时带来的 token，会被 Spring Security 放到这里。
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
                SecurityContextHolder.getContext().getAuthentication().getDetails();

        // Feign 调用 user-service/book-service 时，本质上是 borrow-service 又发起了一次新的 HTTP 请求。
        // 这里把当前用户的 token 取出来，放到这次 Feign 请求的 Authorization 请求头中。
        // 注意 Bearer 后面必须有一个空格，标准格式是：Authorization: Bearer token值
        requestTemplate.header("Authorization", "Bearer " + details.getTokenValue());
    }
}
