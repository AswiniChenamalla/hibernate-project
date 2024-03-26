package com.anp.UserRegister;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserRegisterDAO {

    private final SessionFactory sessionFactory;

    public UserRegisterDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(UserRegister user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserRegister getUserById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(UserRegister.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
