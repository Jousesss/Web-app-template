package ru.alkey.webAppTest.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.alkey.webAppTest.HibernateUtil;
import ru.alkey.webAppTest.models.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    public List<Person> getPeople() {
        List<Person> personList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();  // ОТКРЫТИЕ СЕССИИ И ЕЁ ПОЛУЧЕНИЕ
        try {
            Query query = session.createQuery("FROM Person");  // ПОЛУЧЕНИЕ ВСЕХ ОБЪЕКТОВ ИЗ БД
            personList = query.list();
            System.out.println("People were successfully caught from DataBase!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();  // ЗАКРЫТИЕ СЕССИИ
        }
        return personList;
    }

    public Person getPerson(Long id) {
        Person person = null;
        Session session = HibernateUtil.getSessionFactory().openSession();  // ОТКРЫТИЕ СЕССИИ И ЕЁ ПОЛУЧЕНИЕ
        try {
            person = session.get(Person.class, id);  // ПОЛУЧЕНИЕ ОБЪЕКТА ПО АЙДИ ИЗ БД
            System.out.println("Person with id: " + person.getId() + " was successfully caught from DataBase!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();  // ЗАКРЫТИЕ СЕССИИ
        }
        return person;
    }

    public void addPerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().openSession();  // ОТКРЫТИЕ СЕССИИ И ЕЁ ПОЛУЧЕНИЕ
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();    // ОТКРЫТИЕ ТРАНЗАКЦИИ И ЕЁ ПОЛУЧЕНИЕ
            session.persist(person);   // СОХРАНЕНИЕ СУЩНОСТИ В БД
            transaction.commit();     // КОММИТ ИЗМЕНЕНИЙ (ПОДТВЕРЖДЕНИЕ ДЕЙСТВИЯ)
            System.out.println("Person with id: " + person.getId() + " was successfully added to DataBase!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // ОТКАТ ИЗМЕНЕНИЙ В СЛУЧАЕ ПОЙМАННОЙ ОШИБКИ
            }
            e.printStackTrace();
        } finally {
            session.close();  // ЗАКРЫТИЕ СЕССИИ
        }
    }

    public void updatePerson(Long id, Person person) {
        Session session = HibernateUtil.getSessionFactory().openSession();  // ОТКРЫТИЕ СЕССИИ И ЕЁ ПОЛУЧЕНИЕ
        Transaction transaction = null;
        Person personFromBD;
        try {
            transaction = session.beginTransaction();  // ОТКРЫТИЕ ТРАНЗАКЦИИ И ЕЁ ПОЛУЧЕНИЕ
            personFromBD = session.get(Person.class,id);  // ПОЛУЧЕНИЕ ОБЪЕКТА ПО АЙДИ ИЗ БД
            personFromBD.setName(person.getName());
            personFromBD.setEmail(person.getEmail());
            personFromBD.setAge(person.getAge());
            session.merge(personFromBD);  // ОБНОВЛЕНИЕ ДАННЫХ ОБЪЕКТА
            transaction.commit();  // КОММИТ ИЗМЕНЕНИЙ (ПОДТВЕРЖДЕНИЕ ДЕЙСТВИЯ)
            System.out.println("Person with id: " + person.getId() + " was successfully updated in DataBase!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // ОТКАТ ИЗМЕНЕНИЙ В СЛУЧАЕ ПОЙМАННОЙ ОШИБКИ
            }
            e.printStackTrace();
        } finally {
            session.close();  // ЗАКРЫТИЕ СЕССИИ
        }
    }

    public void removePerson(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();  // ОТКРЫТИЕ СЕССИИ И ЕЁ ПОЛУЧЕНИЕ
        Transaction transaction = null;
        Person person = null;
        try {
            transaction = session.beginTransaction();  // ОТКРЫТИЕ ТРАНЗАКЦИИ И ЕЁ ПОЛУЧЕНИЕ
            person = session.get(Person.class,id);
            session.delete(person);  // УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ
            transaction.commit();  // КОММИТ ИЗМЕНЕНИЙ (ПОДТВЕРЖДЕНИЕ ДЕЙСТВИЯ)
            System.out.println("Person with id: " + person.getId() + " was successfully deleted from DataBase!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // ОТКАТ ИЗМЕНЕНИЙ В СЛУЧАЕ ПОЙМАННОЙ ОШИБКИ
            }
            e.printStackTrace();
        } finally {
            session.close();  // ЗАКРЫТИЕ СЕССИИ
        }
    }
}
