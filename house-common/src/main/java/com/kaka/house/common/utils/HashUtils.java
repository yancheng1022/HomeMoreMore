package com.kaka.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class HashUtils {
    //创建支持MD5加密的function
    private static final HashFunction FUNCTION = Hashing.md5();
    //盐(防止MD5被暴力破解)
    private static final String SALT = "kaka.com";

    public static String encryPassword(String password){
        HashCode hashCode = FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
