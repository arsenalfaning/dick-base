package com.dick.base.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private static final String Password_Format = "%sCHN-KO-USA%s";

    public static String generatePasswordSalt() {
        return IdWorker.get32UUID();
    }

    public static String generatePassword(String password, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return SHA256Util.SHA256(String.format(Password_Format, password, salt));
    }

    public static boolean checkPassword(String passwordOrigin, String salt, String sha256) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return sha256.equals(generatePassword(passwordOrigin, salt));
    }
}
