package com.imageprocessing.imageprocessproject.helper;

public class TokenHolder {
    private static final ThreadLocal<String> jwtTokenThreadLocal = new ThreadLocal<>();

    public static void setToken(String token) {
        jwtTokenThreadLocal.set(token);
    }

    public static String getToken() {
        return jwtTokenThreadLocal.get();
    }


}
