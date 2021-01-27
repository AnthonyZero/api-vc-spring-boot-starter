package com.anthonyzero.api.vc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口版本管理属性
 */
@ConfigurationProperties(prefix="api-version")
@Data
public class ApiVersionProperties {

    private String defaultVersion = "1.0.0";

    private String paramName = "x-api-version";
}
