package edu.mum.mumsched.util;

import java.security.SecureRandom;

public class RandomUtil {

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        return String.format("%05d", num);
    }
}
