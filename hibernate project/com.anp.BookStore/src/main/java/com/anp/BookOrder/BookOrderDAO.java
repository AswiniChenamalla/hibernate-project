package com.anp.BookOrder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.anp.Book.Book;
import com.anp.UserLogin.UserLogin;

import java.util.Collections;
import java.util.List;

public class BookOrderDAO {

    private final SessionFactory sessionFactory;

    public BookOrderDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addOrder(BookOrder order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Book getBookById(int bookId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<BookOrder> getAllOrders(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<BookOrder> query = session.createQuery("from BookOrder where userId = :userId", BookOrder.class);
            query.setParameter("userId", userId);
            return query.list();
        } catch (Exception e) {
        	e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public BookOrder getOrderById(int orderId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BookOrder.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateOrder(BookOrder order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(BookOrder order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
