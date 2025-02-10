package com.TyreseMv.TinyUrl.shortener;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

/*
* This repository will be used for storing hashed urls
* from users who aren't authenticated
* */
@Repository
public class HashedURLRepository {
    private HashMap<String, String> urlMap = new HashMap<>();

    // add url to map
    boolean addUrl(String shortUrl, String longUrl) {
        return urlMap.put(shortUrl, longUrl) != null;
    }
    // get url

    String getUrl(String shortUrl) {
        return urlMap.get(shortUrl);
    }

    // delete url

    boolean deleteUrl(String shortUrl) {
        return urlMap.remove(shortUrl) != null;
    }

    // check if url exists

    boolean urlExists(String shortUrl) {
        return urlMap.containsKey(shortUrl);
    }

    // get all urls



}
