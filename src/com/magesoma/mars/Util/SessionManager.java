package com.magesoma.mars.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by u on 2016/7/16.
 */
public class SessionManager {

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

    /**
     *  open the session
     * @return
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void close() {
        if(!ourSessionFactory.isClosed()){
            ourSessionFactory.close();
        }
    }


}
