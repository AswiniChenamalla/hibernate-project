package com.anp.BookOrder;

import javax.persistence.*;

import com.anp.Book.Book;
import com.anp.UserLogin.UserLogin;

import java.util.Date;

@Entity
@Table(name = "BookOrders")
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "bookId")
    private int bookId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "orderDate")
    private Date orderDate;

    // Constructors
    public BookOrder() {
    }

    public BookOrder(int userId, int bookId, int quantity, Date orderDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
    public BookOrder(UserLogin user, Book book) {
        // Constructor implementation
    }


    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
