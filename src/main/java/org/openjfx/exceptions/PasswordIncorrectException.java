package org.openjfx.exceptions;

public class PasswordIncorrectException extends Exception {

    private String password;

    public PasswordIncorrectException (String password) {
        super(String.format("Incorrect password!"));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}