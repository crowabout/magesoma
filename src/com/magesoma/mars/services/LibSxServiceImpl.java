package com.magesoma.mars.services;
import com.magesoma.mars.bean.BaseBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;

/**
 * Created by u on 2016/7/16.
 */
public class LibSxServiceImpl implements LibSxService {

    private Session session;
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void delById(int id) {
        if (id < 0) {
            throw new NullPointerException("the SQL ID index can't less than 0");
        }
        try {
            session = ourSessionFactory.openSession();
            session.beginTransaction().begin();
//          delByIdImpl(id);
            session.beginTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public synchronized void saveList(List<? extends BaseBean> t) {
        int size = t.size();
        if (size <= 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            saveOne(t.get(i));
        }
    }

    @Override
    public void saveOne(Object t) {
        try {
            session = ourSessionFactory.openSession();
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        } finally {
            session.close();

        }
    }

//    public abstract void saveOneImpl(Object obj);
//    /**
//     * delete one recorder
//     * @param i
//     * @return
//     */
//    public abstract int delByIdImpl(int i) ;


    @Override
    public int insert() {
        return 0;
    }

    @Override
    public void update() {

    }

}
