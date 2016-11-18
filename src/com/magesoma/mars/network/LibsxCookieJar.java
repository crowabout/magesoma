package com.magesoma.mars.network;
import com.magesoma.mars.bean.CookieBean;
import com.magesoma.mars.services.LibSxServiceImpl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by u on 2016/7/16.
 */
public class LibsxCookieJar implements CookieJar {

    private LibSxServiceImpl service;

    public LibsxCookieJar() {
        service = new LibSxServiceImpl();
    }

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
//        service.saveList(cookieListToCookieBean(list));
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {

        return null;
    }

    private CookieBean cookie2CookieBean(Cookie c) {
        CookieBean bean = new CookieBean();
        bean.setDomain(c.domain());
        bean.setSecurity(c.secure());
        bean.setOnlyHttp(c.httpOnly());
        bean.setName(c.name());
        bean.setContent(c.value());
        bean.setExpire(new Date(c.expiresAt()));
        bean.setPath(c.path());
        return bean;
    }


    private List<CookieBean> cookieListToCookieBean(List<Cookie> cl) {

        List<CookieBean> source = new ArrayList<CookieBean>();
        for (int i = 0; i < cl.size(); i++) {
            source.add(cookie2CookieBean(cl.get(i)));
        }
        return source;
    }


    /**
     * 将 List<String> 转换成 List<Cookie>
     *
     * @return
     */
    public List<Cookie> lString2LCookie(List<String> ls) {
        List<Cookie> lc=new ArrayList<Cookie>();
        for(int i=0;i<ls.size();i++){
            lc.add(str2Cookie(ls.get(i)));
        }
        return lc;
    }


    /**
     * 将cookie字符串  转化成一个Cookie
     * @param s
     * @return
     */
    private Cookie str2Cookie(String s) {
        final String seaptor = ";";
        Cookie.Builder builder =new Cookie.Builder();
        Cookie c = null;
        if(s.isEmpty()){
            return c=builder.domain("/").name("").value("").build();
        }
        if(!s.toLowerCase().contains(DOMAIN)){
            if(s.endsWith(";")){
                s+="domain=lib.sx.cn";
            }else{
                s+=";domain=lib.sx.cn";
            }
        }

        if (!s.contains(seaptor)) {
            cookieAttributeParse(s,builder);
            return builder.build();
        }
        String[] temp = s.split(seaptor);
        for(int i=0;i<temp.length;i++){
           cookieAttributeParse(temp[i],builder);
        }
        //强行将过期时间设置为现在
        builder.expiresAt(new Date().getTime());
        return builder.build();
    }


    /**
     * 解析单个的Cookie 的属性.
     * @param attr
     * @param builder
     */
    private void cookieAttributeParse(String attr, Cookie.Builder builder) {
        if (attr.isEmpty()) {
            return;
        }
        if (attr.contains("=")) {
            String nameValue[] = attr.split("=",-1);
            if (nameValue[0].toLowerCase().trim().equals(DOMAIN)) {
                builder.domain(nameValue[1]);
                return;
            }
            else if (nameValue[0].toLowerCase().trim().equals(EXPIRES)) {
//                builder.expiresAt(formatString(nameValue[1]).getTime());
                builder.expiresAt(new Date().getTime());
                return;
            }
        else if (nameValue[0].toLowerCase().trim().equals(PATH)) {
                builder.path(nameValue[1]);
                return;
            } else {
                builder.name(nameValue[0]).value(nameValue[1]);
                return;

            }
        } else {
            String attrLowCase = attr.toLowerCase();
            if (attrLowCase.equals(SECURE)) {
                        builder.secure();
                return;
            }
            if (attrLowCase.equals(HTTP_ONLY)) {
                        builder.httpOnly();
                return;
            }
        }

    }


    private Date formatString(String date) {

        Date d = null;
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        sdf.applyPattern("EEE,dd-mm-yy hh:mm:ss z");
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;

    }


    public static final String DOMAIN = "domain";
    public static final String EXPIRES = "expires";
    public static final String PATH = "path";
    public static final String SECURE = "secure";
    public static final String HTTP_ONLY = "httponly";


}
