package com.anp.UserRegister;

import javax.persistence.*;
	import javax.validation.constraints.NotNull;

	@Entity
	@Table(name = "Users")
	public class UserRegister {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "userId")
	    private int userid;

	    @NotNull
	    @Column(name = "USERNAME", columnDefinition = "VARCHAR(50) NOT NULL")
	    private String username;

	    @NotNull
	    @Column(name = "PHONE", columnDefinition = "VARCHAR(10) NOT NULL")
	    private String phone;

	    @NotNull
	    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(50) NOT NULL")
	    private String password;

	    // Constructors
	    public UserRegister() {
	    }

	    public UserRegister(String username, String phone, String password) {
	        this.username = username;
	        this.phone = phone;
	        this.password = password;
	    }

	    // Getters and setters
	    public int getUserId() {
	        return userid;
	    }

	    public void setUserId(int userId) {
	        this.userid = userId;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	 
	    }

	    @Override
	    public String toString() {
	        return "UserRegister [\n"
	                + "userid=" + userid + "\n"
	                + "username=" + username + "\n"
	                + "phone=" + phone + "\n"
	                + "password=" + password + "\n"
	                + "]";
	    }

	}
