package com.example.demo.common.enums;

public enum AuthCacheKey {
    BASE_TOKEN("USER_AUTH_TOKEN");

    AuthCacheKey(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
