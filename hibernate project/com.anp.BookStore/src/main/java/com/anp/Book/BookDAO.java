package com.anp.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDAO {

    private final SessionFactory sessionFactory;

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> getAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Book", Book.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
}

