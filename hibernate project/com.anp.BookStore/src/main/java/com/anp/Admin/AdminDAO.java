package com.anp.Admin;

import com.anp.UserRegister.UserRegister;
import com.anp.Book.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class AdminDAO {

    private final SessionFactory sessionFactory;

    public AdminDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void addAdmin(Admin admin) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(admin);
            session.getTransaction().commit();
        }
    }
    public List<Admin> getAllAdmins() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Admin", Admin.class).list();
        }
    }

    public void addUser(UserRegister user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void updateUser(UserRegister user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void deleteUser(UserRegister user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public List<UserRegister> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserRegister", UserRegister.class).list();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public void addBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void updateBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public void deleteBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            handleException(e);
        }
    }

    public List<Book> getAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Book", Book.class).list();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public boolean isBookAvailable(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(*) from Book where bookId = :bookId", Long.class);
            query.setParameter("bookId", book.getBookId());
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            handleException(e);
            return false;
        }
    }

    public UserRegister getUserById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(UserRegister.class, userId);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    private void handleException(Exception e) {
        // You can log the error or throw a custom exception here
        e.printStackTrace();
    }

    public Admin getAdminByUsernameAndPassword(String adminUsername, String adminPassword) {
        try (Session session = sessionFactory.openSession()) {
            Query<Admin> query = session.createQuery("from Admin where username = :username and password = :password", Admin.class);
            query.setParameter("username", adminUsername);
            query.setParameter("password", adminPassword);
            return query.uniqueResult();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

}
