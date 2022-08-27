package com.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    private final static Base64.Decoder decoder = Base64.getDecoder();

    public static String decode(String encoded_password) {
        String pwd = null;
        try {
            pwd = new String(decoder.decode(encoded_password), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pwd;
    }
}
