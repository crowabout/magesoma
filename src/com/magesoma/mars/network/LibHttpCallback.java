package com.magesoma.mars.network;

import javax.xml.ws.Response;

/**
 * Created by u on 2016/7/17.
 *  callback of http request
 */
public interface LibHttpCallback {

   /**
    * 返回成功
    * @param result
    * @param code
    */
   public void httpCallbackSuccess(String result,int code);

   /**
    * 返回失败
    * @param result  结果
    * @param code     http code
    * @param failReason reason
    */
   public void httpCallbackFail(String result,int code,String failReason);

}

