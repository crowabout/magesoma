package com.magesoma.mars.services;

import com.magesoma.mars.bean.BaseBean;

import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public interface LibSxService {

        public void delById(int id);
        public void saveOne(Object t);
        public void saveList(List<? extends BaseBean> t);
        public int insert();
        public void update();
}
