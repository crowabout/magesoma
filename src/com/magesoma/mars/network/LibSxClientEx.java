package com.magesoma.mars.network;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/7/21.
 */
public class LibSxClientEx {


    public static final int READ_TIME_OUT=3*60*1000; //milliseconds
    public static final int CONNECTION_TIME_OUT=3*60*1000; //milliseconds

    public static final String URL_LIBSX =
            "http://opac.lib.sx.cn/opac/publicNotice/bookOverdue";
    public static final String HEADER_STR_SET_COOKIES = "set-Cookie";
    private static OkHttpClient client;
    private static OkHttpClient.Builder cBuilder;
    /**
     * 是否打印请求头
     */
    private final boolean isPrintHeader;
    /**
     * 首次请求的时候，server 端返回set-cookie响应头的时候，是否执行保存的动作
     */
    private final boolean saveCookieWhenFirstAccesss;
    private String cookie = "";


    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }


//    public static LibSxClientEx getInstance() {
//        if (instance == null) {
//            instance = new LibSxClientEx();
//        }
//        return instance;
//    }

    private LibSxClientEx(LibSxClientEx.Builder builder) {
        if (client == null) {
            synchronized (LibSxClientEx.class) {
                if (client == null) {
                    client =new OkHttpClient.Builder()
                            .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                            .connectTimeout(CONNECTION_TIME_OUT,TimeUnit.MILLISECONDS)
                            .retryOnConnectionFailure(true)
                            .build();
                }
            }
        }
        isPrintHeader = builder.isPrintHeader;
        saveCookieWhenFirstAccesss = builder.isSaveCookieWhenFirstAccess;

    }


    /**
     * send post request to the server
     *
     * @param url     the url request `
     * @param pageNum requset page number
     * @return
     * @throws IOException
     */
    public String post(String url, String pageNum) throws IOException {
        if (isEmpty(url)) {
            throw new NullPointerException("url is null !!!");
        }

        String body = "";
        System.out.println("------------------------------------------------------");
        System.out.println("              pageNumber: " + pageNum);
        System.out.println("------------------------------------------------------");
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("batchno", "")
                .addEncoded("classNoName", "")
                .addEncoded("classno", "")
                .addEncoded("endtime", "")
                .addEncoded("hasNextPage", "true")
                .addEncoded("language", "")
                .addEncoded("libcode", "")
                .addEncoded("limitDays", "")
                .addEncoded("orderBy", "")
                .addEncoded("page", pageNum)

                .addEncoded("prevPage", "1")
                .addEncoded("rows", "10")
                .addEncoded("searchType", "rdid")
                .addEncoded("searchValue", "")
                .addEncoded("specificClassno", "")

                .addEncoded("specificLibcode", "")
                .addEncoded("starttime", "")
                .addEncoded("state", "")
                .addEncoded("type", "")
                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .header("Host", "opac.lib.sx.cn")
                .header("User-Agent", "Megasoma/5.0 (megasoma MARS rv:32.0.0)")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Referer", "http://opac.lib.sx.cn/opac/publicNotice/bookOverdue")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Connection", "keep-alive");
        if (!isEmpty(cookie)) {
            builder.header("Cookie", cookie);
        }
        builder.post(formBody);
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() && response.code() == 200) {


            //是否打印头部
            if (isPrintHeader) {
                printResponseHeaders(response);
                printRequestHeaders(request);
            }
            body = response.body().string();
        }
        return body;
    }


    /**
     * 发送 get请求 ，获取body的字符串形式
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String GETrString(String url) throws IOException {
        if (isEmpty(url)) {
            throw new NullPointerException("url is null  !!!");
        }
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Megasoma/5.0 (megasoma MARS rv:32.0.0)")
                .build();
        Response response = client.newCall(request).execute();
        String str = response.body().string();

        //若是第一次请求，保存一下cookie
        if (isEmpty(cookie) && saveCookieWhenFirstAccesss) {
                saveCookie(response);
        }
        return str;
    }

    /**
     * 发送GET 请求 ,获取 整个返回Response对象
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Response GETrResponse(String url) throws IOException {
        if (isEmpty(url)) {
            throw new NullPointerException("url is null  !!!");
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    /**
     * 打印响应头
     *
     * @param response
     */
    private void printResponseHeaders(Response response) {
        System.out.println("\n>>>reponse<<<\n" + response.protocol() + " " + response.code() + " " + response.message());
        Headers headers = response.headers();
        Set<String> headerValuesSet = headers.names();
        Iterator<String> headerIteartor = headerValuesSet.iterator();
        while (headerIteartor.hasNext()) {
            String hString = headerIteartor.next();
            System.out.println(hString + " : " + response.header(hString));
        }
    }

    private void printRequestHeaders(Request response) {
        System.out.println("\n>>>request<<<\n" + response.method() + " " + response.url());
        Headers headers = response.headers();
        Set<String> headerValuesSet = headers.names();
        Iterator<String> headerIteartor = headerValuesSet.iterator();
        while (headerIteartor.hasNext()) {
            String hString = headerIteartor.next();
            System.out.println(hString + " : " + response.header(hString));
        }
    }


    /**
     * 保存请求的cookie
     *
     * @param response
     */
    private void saveCookie(Response response) {
        if (response.isSuccessful() && response.code() == 200) {
            String cookie = response.header(HEADER_STR_SET_COOKIES);
            if (!isEmpty(cookie)) {
                splitCookieStr(cookie);
            } else {
                System.err.println("Not find the key  [" + HEADER_STR_SET_COOKIES + "] ");
            }
        }
    }

    /**
     * 将返回的cookie的   JSESSIONID=56E8DFD082F13E0EF65016E39E6F5420; Path=/opac/; HttpOnly 剥离开
     *
     * @param cookiesEntity
     */
    private void splitCookieStr(String cookiesEntity) {

        if (isEmpty(cookiesEntity)) {
            throw new NullPointerException("splitCookieStr(String) cookiesEntity is null!!!");
        }
        String parts[] = cookiesEntity.split(";", -1);
        int length = parts.length;
        if (length <= 0) {
            return;
        }
        cookie = parts[0];
    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static class Builder {
        private boolean isPrintHeader;
        private boolean isSaveCookieWhenFirstAccess;

        public Builder() {
            isPrintHeader = false;
            isSaveCookieWhenFirstAccess = false;
        }

        public LibSxClientEx.Builder isPrintHeader(boolean isPrintHeader) {
            this.isPrintHeader = isPrintHeader;
            return this;
        }

        public LibSxClientEx.Builder isSaveCookieWhenFristAccess(boolean isSave) {
            this.isSaveCookieWhenFirstAccess = isSave;
            return this;
        }

        public LibSxClientEx build() {
            return new LibSxClientEx(this);
        }
    }


    /**
     * asny call
     * @param url
     * @param pageNum
     * @return
     * @throws IOException
     */
    public void postAsyn(String url, String pageNum,Callback callback) throws IOException {
        if (isEmpty(url)) {
            throw new NullPointerException("url is null !!!");
        }

        String body = "";
        System.out.println("------------------------------------------------------");
        System.out.println("              pageNumber: " + pageNum);
        System.out.println("------------------------------------------------------");
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("batchno", "")
                .addEncoded("classNoName", "")
                .addEncoded("classno", "")
                .addEncoded("endtime", "")
                .addEncoded("hasNextPage", "true")
                .addEncoded("language", "")
                .addEncoded("libcode", "")
                .addEncoded("limitDays", "")
                .addEncoded("orderBy", "")
                .addEncoded("page", pageNum)

                .addEncoded("prevPage", "1")
                .addEncoded("rows", "10")
                .addEncoded("searchType", "rdid")
                .addEncoded("searchValue", "")
                .addEncoded("specificClassno", "")

                .addEncoded("specificLibcode", "")
                .addEncoded("starttime", "")
                .addEncoded("state", "")
                .addEncoded("type", "")
                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .header("Host", "opac.lib.sx.cn")
                .header("User-Agent", "Megasoma/5.0 (megasoma MARS rv:32.0.0)")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Referer", "http://opac.lib.sx.cn/opac/publicNotice/bookOverdue")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Connection", "keep-alive");
        if (!isEmpty(cookie)) {
            builder.header("Cookie", cookie);
        }
        builder.post(formBody);
        Request request = builder.build();
                client.newCall(request).enqueue(callback);


//        if (response.isSuccessful() && response.code() == 200) {
//            //是否打印头部
//            if (isPrintHeader) {
//                printResponseHeaders(response);
//                printRequestHeaders(request);
//            }
//            body = response.body().string();
//        }
    }

//    public static void main(String[] args) {
//        String url =URL_LIBSX;
//        LibSxClientEx client =new Builder().isPrintHeader(true).isSaveCookieWhenFristAccess(true).build();
////       client.setCookie("JSESSIONID=576052A2E3C5861EECEE46C017164BC0");
//        try {
//            ParseLibHtml parser =new ParseLibHtml();
//            for(int i=1;i<3;i++){
//               String htmlBody =client.post(URL_LIBSX,String.valueOf(i));
//                parser.parserTagTable(htmlBody);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
