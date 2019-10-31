package com.capgemini.pecunia.util;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.capgemini.pecunia.entity.AccountEntity;
import com.capgemini.pecunia.entity.AddressEntity;
import com.capgemini.pecunia.entity.ChequeEntity;
import com.capgemini.pecunia.entity.CustomerEntity;
import com.capgemini.pecunia.entity.LoanDisbursalEntity;
import com.capgemini.pecunia.entity.LoanRequestEntity;
import com.capgemini.pecunia.entity.LoginEntity;
import com.capgemini.pecunia.entity.TransactionEntity;
public class HibernateUtil {
	private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://remotemysql.com:3306/6IXdRZM9Qz");
                settings.put(Environment.USER, "6IXdRZM9Qz");
                settings.put(Environment.PASS, "rVzUeu2dGF");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(AccountEntity.class);
                configuration.addAnnotatedClass(AddressEntity.class);
                configuration.addAnnotatedClass(ChequeEntity.class);
                configuration.addAnnotatedClass(CustomerEntity.class);
                configuration.addAnnotatedClass(LoanRequestEntity.class);
                configuration.addAnnotatedClass(TransactionEntity.class);
                configuration.addAnnotatedClass(LoanDisbursalEntity.class);
                configuration.addAnnotatedClass(LoginEntity.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
