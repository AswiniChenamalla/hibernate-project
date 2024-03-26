package com.anp.Main;

// Main class
import com.anp.UserLogin.UserLogin; // Import the UserLogin class

import com.anp.Admin.Admin;
import com.anp.Admin.AdminDAO;
import com.anp.Book.Book;
import com.anp.Book.BookDAO;
import com.anp.BookOrder.BookOrder;
import com.anp.BookOrder.BookOrderDAO;
import com.anp.UserLogin.UserLogin;
import com.anp.UserLogin.UserLoginDAO;
import com.anp.UserRegister.UserRegister;
import com.anp.UserRegister.UserRegisterDAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static String readStringInput(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim(); // Trim any leading/trailing whitespace
    }

    private static double readDoubleInput(Scanner scanner, String message) {
        double value;
        while (true) {
            System.out.print(message);
            try {
                value = Double.parseDouble(scanner.nextLine().trim()); // Parse input to double
                break; // Exit loop if input is successfully parsed
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
    
    public static void main(String[] args) {
        // Initialize Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Initialize DAOs
        UserRegisterDAO userRegisterDAO = new UserRegisterDAO(sessionFactory);
        UserLoginDAO userLoginDAO = new UserLoginDAO(sessionFactory);
        BookDAO bookDAO = new BookDAO(sessionFactory);
        AdminDAO adminDAO = new AdminDAO(sessionFactory);
        BookOrderDAO bookOrderDAO = new BookOrderDAO(sessionFactory);

        
        // Create a default admin account if none exists
        createDefaultAdminIfNotExists(adminDAO);
        Admin admin = null; 
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Book Store!");

        do {
            System.out.println("*************** Menu ***************");
            System.out.println("1. Register");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Register User
                    System.out.println("*************** Register ***************");
                    System.out.print("Enter username: ");
                    String regUsername = scanner.next();
                    System.out.print("Enter phone: ");
                    String regPhone = scanner.next();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.next();
                    UserRegister newUser = new UserRegister(regUsername, regPhone, regPassword);
                    userRegisterDAO.addUser(newUser);
                    System.out.println("User registered successfully!");
                    break;

                case 2:
                    // Login as User
                    System.out.println("*************** User Login ***************");
                    System.out.print("Enter username: ");
                    String userUsername = scanner.next();
                    System.out.print("Enter password: ");
                    String userPassword = scanner.next();
                    UserLogin userLogin = userLoginDAO.getUserByUsernameAndPassword(userUsername, userPassword);
                    if (userLogin != null) {
                        System.out.println("Login successful!");
                        displayUserMenu(scanner, userLogin, bookOrderDAO);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3:
                    // Login as Admin
                    System.out.println("*************** Admin Login ***************");
                    System.out.print("Enter username: ");
                    String adminUsername = scanner.next();
                    System.out.print("Enter password: ");
                    String adminPassword = scanner.next();
                    admin = adminDAO.getAdminByUsernameAndPassword(adminUsername, adminPassword); // Update admin
                    if (admin != null) {
                        System.out.println("Admin login successful!");
                        displayAdminMenu(scanner, adminDAO, bookDAO, admin); // Pass the admin object
                    } else {
                        System.out.println("Invalid username or password for admin.");
                    }
                    break;


                case 4:
                    // Exit
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            String continueChoice = scanner.next();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                break;
            }

        } while (choice != 4);

        // Close Hibernate SessionFactory
        sessionFactory.close();
    }
    private static void createDefaultAdminIfNotExists(AdminDAO adminDAO) {
        // Check if any admin account exists
        List<Admin> admins = adminDAO.getAllAdmins();

        if (admins.isEmpty()) {
            // Create a default admin account
            String defaultUsername = "anusha";
            String defaultPassword = "anu123";

            Admin defaultAdmin = new Admin(defaultUsername, defaultPassword);
            adminDAO.addAdmin(defaultAdmin);

            System.out.println("Default admin account created successfully.");
        } else {
            System.out.println("Admin account already exists.");
        }
    }


    private static void displayUserMenu(Scanner scanner, UserLogin userLogin, BookOrderDAO bookOrderDAO) {
        int choice;
        do {
            System.out.println("*************** User Menu ***************");
            System.out.println("1. Add Order");
            System.out.println("2. View All Orders");
            System.out.println("3. Get Order by ID");
            System.out.println("4. Update Order");
            System.out.println("5. Delete Order");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add Order
                    System.out.println("Enter book ID to order: ");
                    int bookId = scanner.nextInt();
                    Book book = bookOrderDAO.getBookById(bookId);
                    if (book != null) {
                        System.out.println("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        BookOrder bookOrder = new BookOrder(userLogin.getUserId(), bookId, quantity, new Date(bookId));
                        bookOrderDAO.addOrder(bookOrder);
                        System.out.println("Order placed successfully!");
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                case 2:
                    // View All Orders
                    List<BookOrder> allOrders = bookOrderDAO.getAllOrders(userLogin.getUserId());
                    if (!allOrders.isEmpty()) {
                        System.out.println("*************** All Orders ***************");
                        for (BookOrder order : allOrders) {
                            System.out.println(order);
                        }
                    } else {
                        System.out.println("No orders found!");
                    }
                    break;

                case 3:
                    // Get Order by ID
                    System.out.print("Enter order ID: ");
                    int orderId = scanner.nextInt();
                    BookOrder orderById = bookOrderDAO.getOrderById(orderId);
                    if (orderById != null) {
                        System.out.println("Order found: " + orderById);
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;

                case 4:
                    // Update Order
                    System.out.print("Enter order ID to update: ");
                    int updateOrderId = scanner.nextInt();
                    BookOrder updateOrder = bookOrderDAO.getOrderById(updateOrderId);
                    if (updateOrder != null) {
                        System.out.println("Enter new quantity: ");
                        int newQuantity = scanner.nextInt();
                        updateOrder.setQuantity(newQuantity);
                        bookOrderDAO.updateOrder(updateOrder);
                        System.out.println("Order updated successfully!");
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;

                case 5:
                    // Delete Order
                    System.out.print("Enter order ID to delete: ");
                    int deleteOrderId = scanner.nextInt();
                    BookOrder deleteOrder = bookOrderDAO.getOrderById(deleteOrderId);
                    if (deleteOrder != null) {
                        bookOrderDAO.deleteOrder(deleteOrder);
                        System.out.println("Order deleted successfully!");
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            String continueChoice = scanner.next();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                break;
            }

        } while (choice != 6);
    }


    private static void displayAdminMenu(Scanner scanner, AdminDAO adminDAO, BookDAO bookDAO, Admin admin) {
        // Your existing code for the displayAdminMenu method
    	if (admin != null) {
    	int choice;
        do {
            System.out.println("*************** Admin Menu ***************");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. View All Users");
            System.out.println("5. Add Book");
            System.out.println("6. Update Book");
            System.out.println("7. Delete Book");
            System.out.println("8. View All Books");
            System.out.println("9. Check Book Availability");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add User
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter phone: ");
                    String phone = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();
                    UserRegister newUser = new UserRegister(username, phone, password);
                    adminDAO.addUser(newUser);
                    System.out.println("User added successfully!");
                    break;

                case 2:
                    // Update User
                    System.out.print("Enter user ID to update: ");
                    int userIdToUpdate = scanner.nextInt();
                    UserRegister updateUser = adminDAO.getUserById(userIdToUpdate);
                    if (updateUser != null) {
                        // Update user details
                        System.out.print("Enter new username: ");
                        String newUsername = scanner.next();
                        updateUser.setUsername(newUsername);
                        System.out.print("Enter new phone: ");
                        String newPhone = scanner.next();
                        updateUser.setPhone(newPhone);
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.next();
                        updateUser.setPassword(newPassword);
                        adminDAO.updateUser(updateUser);
                        System.out.println("User updated successfully!");
                    } else {
                        System.out.println("User not found!");
                    }
                    break;

                case 3:
                    // Delete User
                    System.out.print("Enter user ID to delete: ");
                    int userIdToDelete = scanner.nextInt();
                    UserRegister deleteUser = adminDAO.getUserById(userIdToDelete);
                    if (deleteUser != null) {
                        adminDAO.deleteUser(deleteUser);
                        System.out.println("User deleted successfully!");
                    } else {
                        System.out.println("User not found!");
                    }
                    break;

                case 4:
                    // View All Users
                    List<UserRegister> allUsers = adminDAO.getAllUsers();
                    if (!allUsers.isEmpty()) {
                        System.out.println("*************** All Users ***************");
                        for (UserRegister user : allUsers) {
                            System.out.println(user);
                        }
                    } else {
                        System.out.println("No users found!");
                    }
                    break;
                    
                case 5:
                    // Add Book
                    System.out.print("Enter book title: ");
                    String title = scanner.next(); // Use next() to capture a single word
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine(); // Use nextLine() to capture the entire line
                    double price = readDoubleInput(scanner, "Enter book price: "); // Use the helper method for reading a double
                    Book newBook = new Book(title, author, price);
                    try {
                        adminDAO.addBook(newBook);
                        System.out.println("Book added successfully!");
                    } catch (Exception e) {
                        System.out.println("Failed to add book: " + e.getMessage());
                    }
                    break;

                case 6:
                    // Update Book
                    System.out.print("Enter book ID to update: ");
                    int bookIdToUpdate = scanner.nextInt();
                    Book updateBook = bookDAO.getBookById(bookIdToUpdate);
                    if (updateBook != null) {
                        // Update book details
                        System.out.print("Enter new title: ");
                        String newTitle = scanner.next();
                        updateBook.setTitle(newTitle);
                        System.out.print("Enter new author: ");
                        String newAuthor = scanner.next();
                        updateBook.setAuthor(newAuthor);
                        System.out.print("Enter new price: ");
                        double newPrice = scanner.nextDouble();
                        updateBook.setPrice(newPrice);
                        adminDAO.updateBook(updateBook);
                        System.out.println("Book updated successfully!");
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                case 7:
                    // Delete Book
                    System.out.print("Enter book ID to delete: ");
                    int bookIdToDelete = scanner.nextInt();
                    Book deleteBook = bookDAO.getBookById(bookIdToDelete);
                    if (deleteBook != null) {
                        adminDAO.deleteBook(deleteBook);
                        System.out.println("Book deleted successfully!");
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                case 8:
                    // View All Books
                    List<Book> allBooks = bookDAO.getAllBooks();
                    if (!allBooks.isEmpty()) {
                        System.out.println("*************** All Books ***************");
                        for (Book book : allBooks) {
                            System.out.println(book);
                        }
                    } else {
                        System.out.println("No books found!");
                    }
                    break;

                case 9:
                    // Check Book Availability
                    System.out.print("Enter book ID to check availability: ");
                    int checkBookId = scanner.nextInt();
                    Book checkBook = bookDAO.getBookById(checkBookId);
                    if (checkBook != null) {
                        if (adminDAO.isBookAvailable(checkBook)) {
                            System.out.println("Book is available!");
                        } else {
                            System.out.println("Book is not available!");
                        }
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                case 10:
                    // Exit
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            String continueChoice = scanner.next();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                break;
            }

        } while (choice != 10);
       
    }
    	else {
    		 System.out.println("You must be logged in as admin to access the admin menu.");
    	}
}}
