import com.magesoma.mars.bean.BooksBean;
import com.magesoma.mars.bean.CookieBean;
import com.magesoma.mars.bean.DownloadLogBean;
import com.magesoma.mars.network.LibsxCookieJar;
import com.magesoma.mars.services.LibSxServiceImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public class Main {
    public static final String targetUrl="http://opac.lib.sx.cn/opac/publicNotice/bookOverdue";

    public static void main(String[] args) {

        OkHttpClient ohc=new OkHttpClient();
        LibsxCookieJar jar =new LibsxCookieJar();
        OkHttpClient.Builder okBuilder=new OkHttpClient.Builder().cookieJar(jar);
        Request request = new Request.Builder().url(targetUrl).build();
        try {
            Response response = ohc.newCall(request).execute();
            List<String> header =response.headers("set-cookie");
            jar.saveFromResponse(null,jar.lString2LCookie(header));
//            Headers headers =response.headers();
//            System.out.println(headers.names());
            String body =response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        test();

    }


    public static void test(){
        LibSxServiceImpl service =new LibSxServiceImpl();
        CookieBean bean =new CookieBean();
        bean.setContent("acd2fe36err343fe3");
        bean.setDomain("http://www.baidu.com");
        bean.setExpire(new Date());
        bean.setName("jessionid");
        bean.setOnlyHttp(false);
        bean.setSize(100);

        CookieBean bean2=null ;
        CookieBean bean3=null ;
        try {
            bean2=bean.getClass().newInstance();
            bean2.setSize(200);
            bean2.setContent("acd2fe36err343fe3");
            bean2.setDomain("http://www.baidu.com");
            bean2.setExpire(new Date());
            bean2.setName("jessionid");
            bean3 =bean.getClass().newInstance();
            bean3.setSize(300);
            bean3.setContent("acd2fe36err343fe3");
            bean3.setDomain("http://www.baidu.com");
            bean3.setExpire(new Date());
            bean3.setName("jessionid");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        DownloadLogBean log=new DownloadLogBean();
        log.setBeginTime(new Date(System.currentTimeMillis()));
        log.setEndTime(new Date(System.currentTimeMillis()));
        log.setPageNum(20);


        List<CookieBean> source=new ArrayList<CookieBean>();
        source.add(bean);
        source.add(bean2);
        source.add(bean3);
        service.saveList(source);
        service.saveOne(log);
    }
}
