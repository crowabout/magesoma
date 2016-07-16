package com.magesoma.mars.network;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public class LibsxCookieJar implements CookieJar{

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {


    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {

        return null;
    }
}
