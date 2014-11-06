package lv.rtu.domain;

import java.math.BigInteger;
import java.security.SecureRandom;

public class AuthorizationTokenGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String nextToken() {
        return new BigInteger(130, random).toString(32);
    }
}
