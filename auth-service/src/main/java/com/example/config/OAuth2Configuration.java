package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;

@EnableAuthorizationServer   //开启验证服务器
@Configuration
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager manager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 这个方法是对客户端进行配置，一个验证服务器可以预设很多个客户端，
     * 之后这些指定的客户端就可以按照下面指定的方式进行验证
     *
     * @param clients 客户端配置工具
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //这里我们直接硬编码创建，当然也可以像Security那样自定义或是使用JDBC从数据库读取
                .inMemory()
                //客户端名称，随便起就行
                .withClient("web")
                //只与客户端分享的secret，随便写，但是注意要加密
                .secret(encoder.encode("654321"))
                //自动审批，这里关闭，要的就是一会体验那种感觉
                .autoApprove(false)
                //授权范围，这里我们使用全部all
                .scopes("book", "user", "borrow")
                //可以写多个，当有多个时需要在验证请求中指定使用哪个地址进行回调
                .redirectUris("http://localhost:8180/login",
                        "http://localhost:8280/login", "http://localhost:8380/login")

                .authorizedGrantTypes("client_credentials", "password", "implicit",
                        "authorization_code", "refresh_token");
        //授权模式，一共支持5种，除了之前我们介绍的四种之外，还有一个刷新Token的模式
        //这里我们直接把五种都写上，方便一会实验，当然各位也可以单独只写一种一个一个进行测试
        //现在我们指定的客户端就支持这五种类型的授权方式了
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                //编码器设定为BCryptPasswordEncoder
                .passwordEncoder(encoder)
                //允许客户端使用表单验证，一会我们POST请求中会携带表单信息
                .allowFormAuthenticationForClients()
                //允许所有的Token查询请求
                .checkTokenAccess("permitAll()");
    }

    @Resource
    UserDetailsService service;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .userDetailsService(service)
                .authenticationManager(manager);
    }

}
