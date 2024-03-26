package com.anp.UserLogin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserLoginDAO {

    private final SessionFactory sessionFactory;

    public UserLoginDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserLogin getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserLogin where username = :username", UserLogin.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserLogin getUserByUsernameAndPassword(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserLogin where username = :username and password = :password", UserLogin.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
