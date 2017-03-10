package com.framgia.toandoan.apiservice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by framgia on 10/03/2017.
 */
public class APIUtils {
    public static final String API_KEY = "1b8e5f0c7c68645e764aab05b6cb294f";
    public static final String PRIVATE_KEY = "e7bc4ee43eeb712ed69bd671d14c796db640001f";
    public static final String BASE_URL = "https://gateway.marvel.com:443";

    public static String getKey(long timeStamp) {
        String stringToHash = timeStamp + PRIVATE_KEY + API_KEY;
        return md5(stringToHash);
    }

    public static final String md5(final String s) {
        final String md5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                .getInstance(md5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
