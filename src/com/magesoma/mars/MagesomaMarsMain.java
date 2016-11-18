package com.magesoma.mars;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by u on 2016/7/15.
 */
public class MagesomaMarsMain {
    public static void main(String[] args) {

//            int threadNum =10;
//
//               ExecutorService dservice = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

//                MarsManager.setPageIndex(2456);
                for(int i=0;i<10;i++){
                    new Thread(new DThreader()).start();
                }


    }


    static class DThreader implements Runnable{
        public void run(){
            System.out.println(" threadName:"+Thread.currentThread().getName());
                MarsManager.getInstance().download(true);
        }
    }

}
