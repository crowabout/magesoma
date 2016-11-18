package com.magesoma.mars;

import com.magesoma.mars.bean.DownloadLogBean;
import com.magesoma.mars.bean.ReaderInfoBean;
import com.magesoma.mars.network.LibSxClientEx;
import com.magesoma.mars.network.ParseLibHtml;
import com.magesoma.mars.services.LibSxServiceImpl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by u on 2016/7/23.
 * Megosoma MArs manager
 */
public class MarsManager {


    /**
     * 页面索引
     */
    private static volatile int pageIndex = 0;
    private int totalPage = 0;
    private LibSxClientEx client;
    private ParseLibHtml parser;
    private LibSxServiceImpl service;

    private static MarsManager instance;

    public static void setPageIndex(int pageIndex) {
        MarsManager.pageIndex = pageIndex;
    }

    public static MarsManager getInstance(){
        if(instance==null){
           synchronized (MarsManager.class){
               if(instance==null)
                     instance =new MarsManager();
            }
        }
        return instance;
    }




     private MarsManager() {
        client = new LibSxClientEx.Builder().isSaveCookieWhenFristAccess(true).build();
        parser = new ParseLibHtml();
        service = new LibSxServiceImpl();

    }

    /**
     * begin downing
     * @param isAsyn
     */
    public void download(boolean isAsyn) {
        System.out.println("=======the Number "+pageIndex+"==============");
        //设置cookie 和 获取总的页数
        if (LibSxClientEx.isEmpty(client.getCookie())) {
            String doc = "";
            try {
                //首次请求get 方式,会保存cookie
                doc = client.GETrString(LibSxClientEx.URL_LIBSX);
                //解析网页数据,抽取总的页数
                totalPage = parser.parseTagSpanForPage(doc);
                System.err.println("=========="+totalPage+"==========");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //开始下载
        if(isAsyn)
        {
            dpageAsyn();
        }else{
            dpage();
        }

    }

    public synchronized void dpage() {
        System.out.println("begin download.....");
        try {
            while (pageIndex < totalPage) {
                String body = client.post(LibSxClientEx.URL_LIBSX, String.valueOf(pageIndex));
                System.out.println("current page index:    "+pageIndex);
                increPageIndex();
                List<ReaderInfoBean> source = parser.parserTagTable(body);
                service.saveList(source);
                log();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("downlaod finsh!!!!");
    }


    public synchronized void dpageAsyn() {
        System.out.println("begin download.....");
        try {
            while (pageIndex < totalPage) {
                client.postAsyn(LibSxClientEx.URL_LIBSX, String.valueOf(pageIndex), postCallback);
                System.out.println("current page index:    " + pageIndex);
                increPageIndex();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("downlaod finsh!!!!");
    }



     Callback postCallback =new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            return;
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            List<ReaderInfoBean> source = parser.parserTagTable(response.body().string());
            service.saveList(source);
            log();

        }
    };



    private static synchronized void increPageIndex() {
        pageIndex++;
    }

    private void log() {
        DownloadLogBean log = new DownloadLogBean();
        log.setBeginTime(new Date());
        log.setPageNum(pageIndex);
        service.saveOne(log);
    }

//    public static void main(String[] args) {
//
//            getInstance().download();
//
//    }

}
