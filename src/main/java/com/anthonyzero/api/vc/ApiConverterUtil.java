package com.anthonyzero.api.vc;

import org.springframework.util.StringUtils;

//静态方法将字符创转为ApiItem
public class ApiConverterUtil {

    /**
     * 将版本号1.0.0 转为ApiItem类型
     * @param version
     * @return
     */
    public static ApiItem convert(String version) {
        ApiItem apiItem = new ApiItem();
        if (StringUtils.isEmpty(version)) {
            return apiItem;
        }

        String[] arr = version.split( "\\.");
        apiItem.setHigh(Integer.parseInt(arr[0])); //高位
        if (arr.length > 1) {
            apiItem.setMid(Integer.parseInt(arr[1]));  //中位
        }
        if (arr.length > 2) {
            apiItem.setLow(Integer.parseInt(arr[2])); //低位
        }
        return apiItem;
    }
}
