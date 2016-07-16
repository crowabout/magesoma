package com.magesoma.mars.services;

import com.magesoma.mars.bean.BaseBean;

import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public interface LibSxService {

        public void delById(int id);
        public void save(List<?> t);
        public int insert();
        public void update();
}
