package org.example.web.dto;

import java.util.HashMap;
import java.util.Map;

public class LoginForm {

    private String username;
    private String password;
    private static final HashMap<String, String> keyPas = new HashMap<>();

    //Initialization root user login/password
    static {
        keyPas.put("root", "123");
    }

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginForm() {
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

    public static String getKeyPas(String login) {
        return keyPas.get(login);
    }

    public static void setKeyPas(String login, String password) {
        LoginForm.keyPas.put(login, password);
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
