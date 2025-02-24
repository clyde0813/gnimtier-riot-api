package com.gnimtier.riot.utils;

import com.gnimtier.riot.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private HashUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            // 바이트 배열을 16진수로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
