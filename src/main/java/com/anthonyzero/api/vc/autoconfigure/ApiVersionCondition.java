package com.anthonyzero.api.vc.autoconfigure;

import com.anthonyzero.api.vc.ApiControlException;
import com.anthonyzero.api.vc.ApiConverterUtil;
import com.anthonyzero.api.vc.ApiItem;
import com.anthonyzero.api.vc.ApiVersionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    private ApiItem version;

    private boolean NULL;

    //默认配置
    private final ApiVersionProperties DEFAULT_PROPERTIES;

    //给定的默认版本
    private final ApiItem DEFAULT_API_ITEM;

    public ApiVersionCondition(ApiItem item, ApiVersionProperties properties) {
        this.version = item;
        this.DEFAULT_PROPERTIES = properties;
        this.DEFAULT_API_ITEM = ApiConverterUtil.convert(properties.getDefaultVersion());
    }

    public ApiVersionCondition(ApiItem item, boolean NULL, ApiVersionProperties properties) {
        this.version = item;
        this.NULL = NULL;
        this.DEFAULT_PROPERTIES = properties;
        this.DEFAULT_API_ITEM = ApiConverterUtil.convert(properties.getDefaultVersion());
    }

    /**
     * 某个接口有多个规则时，进行合并 比如类上指定了@RequestMapping的 url 为 root - 而方法上指定的@RequestMapping的 url 为 method
     * 最后这个接口匹配成root/method
     *
     * Spring先扫描方法再扫描类，然后调用
     * 按照方法上的注解优先级大于类上注解的原则处理，但是要注意如果方法上不定义注解的情况。
     * 如果方法或者类上不定义注解，我们会给一个默认的值
     * @param other 方法扫描封装结果
     * @return
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        if (other.NULL) {
            return this;
        }
        return other;
    }

    //判断是否符合当前请求，返回null表示不符合 在所有的符合要求的 版本中1.0.0 1.2.0 等比较中找最高的一个
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return new ApiVersionCondition(DEFAULT_API_ITEM, DEFAULT_PROPERTIES);
        }
        //从请求头拿到版本号
        String version = request.getHeader(DEFAULT_PROPERTIES.getParamName());
        // 获取所有小于等于版本的接口;如果前端不指定版本号，则默认请求1.0.0版本的接口
        if (StringUtils.isEmpty(version)) {
            log.warn("未指定版本，使用默认{}版本。", DEFAULT_PROPERTIES.getDefaultVersion());
            version = DEFAULT_PROPERTIES.getDefaultVersion();
        }
        ApiItem item = null;
        try {
            item = ApiConverterUtil.convert(version);
        } catch (Exception e) {
            throw new ApiControlException("版本号格式不正确");
        }
        if (item.compareTo(DEFAULT_API_ITEM) < 0) {
            String msg = String.format("请求头API版本[%s]错误，最低版本[%s]", version, DEFAULT_PROPERTIES.getDefaultVersion());
            throw new ApiControlException(msg);
        }
        if (item.compareTo(this.version) >= 0) {
            //this这个版本 满足要求
            return this;
        }
        return null;
    }

    //针对指定的请求对象request发现有多个满足条件的，用来排序指定优先级，使用最优的进行响应
    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 获取到多个符合条件的接口后，会按照这个排序，然后get(0)获取最大版本对应的接口.自定义条件会最后比较
        int compare = other.version.compareTo(this.version);
        if (compare == 0) {
            log.warn("RequestMappingInfo相同，请检查！version:{}", other.version);
        }
        return compare;
    }
}
