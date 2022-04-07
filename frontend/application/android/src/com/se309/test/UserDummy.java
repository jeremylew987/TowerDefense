package com.se309.test;

/**
 * UserDummy.java
 *
 * This class exists to be used in different tests of the network serialization and deserialization code
 */

public class UserDummy {

    private String username;
    private String password;

    /**
     * Basic constructor
     * @param username The username
     * @param password The password
     */
    public UserDummy(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Equality override
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof UserDummy) {

            UserDummy other = (UserDummy) o;

            if (other.username.equals(username) && other.password.equals(password)) return true;

            return false;
        } else {
            return false;
        }
    }

    /**
     * Getters and setters, you already know what these do
     */

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
}
