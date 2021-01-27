package com.anthonyzero.api.vc;


import lombok.Data;

/**
 * 将版本号抽象为ApiItem类
 */
@Data
public class ApiItem implements Comparable<ApiItem>{
    private int high = 1;

    private int mid = 0;

    private int low = 0;


    public ApiItem() {
    }

    //实现Comparable接口并重写compareTo(),从高位到低位依次比较。
    @Override
    public int compareTo(ApiItem o) {
        if (this.getHigh() > o.getHigh()) {
            return 1;
        } else if (this.getHigh() < o.getHigh()) {
            return -1;
        }
        //高位相等 比较中位
        if (this.getMid() > o.getMid()) {
            return 1;
        } else if (this.getMid() < o.getMid()) {
            return -1;
        }
        //高中位相等 比较低位
        if (this.getLow() > o.getLow()) {
            return 1;
        } else if (this.getLow() < o.getLow()) {
            return -1;
        }
        //高 中 低都相等
        return 0;
    }
}
