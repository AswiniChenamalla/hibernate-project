package com.anp.UserLogin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Users")
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @NotNull
    @Column(name = "USERNAME", columnDefinition = "VARCHAR(50) NOT NULL")
    private String username;

    @NotNull
    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(50) NOT NULL")
    private String password;

    // Constructors
    public UserLogin() {
    }

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}
}
