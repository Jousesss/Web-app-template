package ru.alkey.webAppTest;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.alkey.webAppTest.models.Person;


public class HibernateUtil {
    private static final String DB_URL = "url";
    private static final String DB_USERNAME = "name";
    private static final String DB_PASSWORD = "password";

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.url",DB_URL)
                        .setProperty("hibernate.connection.username",DB_USERNAME)
                        .setProperty("hibernate.connection.password",DB_PASSWORD)
                        .setProperty("hibernate.connection.driver_class","org.postgresql.Driver")
                        .setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
                configuration.addAnnotatedClass(Person.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Не получилось получить session factory!\n" + e);
            }
        }
        return sessionFactory;
    }
}
