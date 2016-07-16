package com.magesoma.mars.services;

import com.magesoma.mars.Util.SessionManager;
import org.hibernate.Session;
import org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode;

import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public class LibSxServiceImpl implements LibSxService{

    private static Session session;
    public LibSxServiceImpl(){
        if(session==null){
            session = SessionManager.getSession();
        }
    }


    @Override
    public void delById(int id) {



    }

    @Override
    public void save(List<?> t) {

    }

    @Override
    public int insert() {
        return 0;
    }

    @Override
    public void update() {

    }
}
