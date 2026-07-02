package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // borrow-service 本身也是资源服务。
                // 外部访问 /borrow/** 时必须携带包含 borrow 权限范围的 Bearer Token。
                .anyRequest().access("#oauth2.hasScope('borrow')");
    }
}
