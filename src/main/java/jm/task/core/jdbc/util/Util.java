package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/somebd";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";

    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("This project has been connected to database" + URL);
            }
        } catch (java.sql.SQLException e) {
            System.out.println("There is no connection(\n");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory(){
        SessionFactory local = sessionFactory;
        if (local == null || local.isClosed()){
            synchronized (Util.class){
                local = sessionFactory;
                try{
                    Configuration config = new Configuration();
                    Properties settings = new Properties();
                    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(Environment.URL, URL);
                    settings.put(Environment.USER, NAME);
                    settings.put(Environment.PASS, PASSWORD);
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                    settings.put(Environment.SHOW_SQL, "true");
                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                    settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                    config.setProperties(settings);
                    config.addAnnotatedClass(User.class);
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(config.getProperties()).build();
                    sessionFactory = local = config.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    System.out.println("Hibernate Connection Error!!!");
                }
            }
        }
        return local;
    }
}