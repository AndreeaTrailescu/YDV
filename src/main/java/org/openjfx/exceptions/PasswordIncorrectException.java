package org.openjfx.exceptions;

public class PasswordIncorrectException extends Exception {

    private String password;

    public PasswordIncorrectException (String password) {
        super(String.format("Username already exists or the password entered is incorrect!"));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}