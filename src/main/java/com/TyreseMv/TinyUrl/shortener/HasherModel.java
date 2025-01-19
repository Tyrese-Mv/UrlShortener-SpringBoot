package com.TyreseMv.TinyUrl.shortener;

import org.apache.commons.codec.digest.DigestUtils;

/*
* Responsible for hashing
* */
public class HasherModel {

    private static final int MAXLENGTH = 7;

    public static String HashURL(String URL){
        String hashedUrl = DigestUtils.sha256Hex(URL);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i <= MAXLENGTH; i++){
            sb.append(hashedUrl.charAt(i));
        }
        return sb.toString();
    }

}
