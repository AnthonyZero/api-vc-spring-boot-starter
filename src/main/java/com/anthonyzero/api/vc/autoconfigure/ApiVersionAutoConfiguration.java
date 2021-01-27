package com.anthonyzero.api.vc.autoconfigure;

import com.anthonyzero.api.vc.ApiVersionProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnWebApplication
@Configuration
@EnableConfigurationProperties(ApiVersionProperties.class)
public class ApiVersionAutoConfiguration {

    //当给定的在bean不存在时,则实例化当前Bean 如果没配置给定默认配置
    @ConditionalOnMissingBean
    @Bean
    public ApiVersionProperties apiVersionProperties() {
        return new ApiVersionProperties();
    }

    @Bean
    public ApiVersionWebMvcRegistrations apiVersionWebMvcRegistrations(ApiVersionProperties apiVersionProperties) {
        return new ApiVersionWebMvcRegistrations(apiVersionProperties);
    }
}
