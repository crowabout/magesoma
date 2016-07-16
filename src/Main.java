import com.magesoma.mars.Util.SessionManager;
import com.magesoma.mars.bean.Books;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by u on 2016/7/16.
 */
public class Main {
    public static void main(final String[] args) throws Exception {
        final Session session = SessionManager.getSession();
        try {
            System.out.println("querying all the managed entities...");
            session.beginTransaction();
            List result =session.createQuery( "from Books " ).list();
            for(Books b:(List<Books>)result){
                System.out.println("Books "+b.getBookName());
            }
            System.out.println("execute finish.............");
        } finally {
           SessionManager.close();
        }
    }
}
