package ru.job4j.early;

import java.util.*;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetterOrDigit;

public class PasswordValidator {
    private static final Set<String> STOP_STRINGS = Set.of("qwerty", "12345", "password", "admin", "user");

    @SuppressWarnings("checkstyle:SimplifyBooleanExpression")
    public static String validate(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password can't be null");
        }
        if (password.length() < 8 || password.length() > 32) {
            throw new IllegalArgumentException("Password should be length [8, 32]");
        }
        if (password.toLowerCase().equals(password)) {
            throw new IllegalArgumentException("Password should contain at least one uppercase letter");
        }
        if (password.toUpperCase().equals(password)) {
            throw new IllegalArgumentException("Password should contain at least one lowercase letter");
        }
        boolean digits = false;
        boolean specials = false;
        for (char ch : password.toCharArray()) {
            if (isDigit(ch)) {
                digits = true;
            } else if (!isLetterOrDigit(ch)) {
                specials = true;
            }
            if (digits && specials) {
                break;
            }
        }
        if (!digits) {
            throw new IllegalArgumentException("Password should contain at least one figure");
        }
        if (!specials) {
            throw new IllegalArgumentException("Password should contain at least one special symbol");
        }
        for (String s : STOP_STRINGS) {
            if (password.toLowerCase().contains(s)) {
                throw new IllegalArgumentException("Password shouldn't contain substrings: "
                        + "qwerty, 12345, password, admin, user");
            }
        }
        return password;
    }

}