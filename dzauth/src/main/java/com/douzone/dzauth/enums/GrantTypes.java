package com.douzone.dzauth.enums;

public enum GrantTypes {
    AUTHORIZATION_CODE("authorization_code"), REFRESH_TOKEN("refresh_token"), PASSWORD("password");
    String value;

    GrantTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
