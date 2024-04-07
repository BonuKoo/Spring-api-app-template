package com.app.global.jwt.constant;

public enum TokenType {

    ACCESS,REFRESH;

    public static boolean isAccessToken(String tokenType) {

        return  TokenType.ACCESS.name().equals(tokenType);
        //파라미터로 넘어온 토큰 타입이 access가 아니면 false, 맞으면 true 반환

    }
}
