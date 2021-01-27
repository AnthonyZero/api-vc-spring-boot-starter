package com.anthonyzero.api.vc.autoconfigure;

import com.anthonyzero.api.vc.ApiConverterUtil;
import com.anthonyzero.api.vc.ApiVersionProperties;
import com.anthonyzero.api.vc.annotation.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.*;

//配置类
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    private final ApiVersionProperties defaultProperties;

    public ApiVersionHandlerMapping(ApiVersionProperties apiVersionProperties) {
        this.defaultProperties = apiVersionProperties;
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return createCondition(method.getClass());
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return createCondition(handlerType);
    }


    private ApiVersionCondition createCondition(Class<?> clazz) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(clazz, ApiVersion.class);
        return apiVersion == null ? getDefaultCondition() :
                new ApiVersionCondition(ApiConverterUtil.convert(apiVersion.value()), defaultProperties);
    }

    private ApiVersionCondition getDefaultCondition(){
        return new ApiVersionCondition(ApiConverterUtil.convert(defaultProperties.getDefaultVersion()),true, defaultProperties);
    }
}
