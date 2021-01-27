package com.anthonyzero.api.vc.autoconfigure;

import com.anthonyzero.api.vc.ApiVersionProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ApiVersionWebMvcRegistrations implements WebMvcRegistrations {

    private final ApiVersionProperties apiVersionProperties;

    public ApiVersionWebMvcRegistrations(ApiVersionProperties apiVersionProperties) {
        this.apiVersionProperties = apiVersionProperties;
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping(apiVersionProperties);
    }
}
