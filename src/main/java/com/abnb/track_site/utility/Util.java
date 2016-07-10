package com.abnb.track_site.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    public static String toSHA1(String convertme) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return new String(md.digest(convertme.getBytes()));
    }
}
