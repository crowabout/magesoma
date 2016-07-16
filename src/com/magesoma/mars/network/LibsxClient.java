package com.magesoma.mars.network;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public class LibsxClient {
    static String targeturl ="http://opac.lib.sx.cn/opac/reader/doLogin";

    public static void main(String[] args) {

        OkHttpClient ohc=new OkHttpClient();


        OkHttpClient.Builder okBuilder=new OkHttpClient.Builder().cookieJar();

        Request request = new Request.Builder().url(targeturl).build();
        try {
            Response response = ohc.newCall(request).execute();

            Headers headers =response.headers();
            System.out.println(headers.names());
            String body =response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
