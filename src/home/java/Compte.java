package home.java;

import org.apache.commons.codec.digest.DigestUtils;

public class Compte {
    private String email;
    private String login;
    private String password;

    public Compte(String email, String login, String password) {

        this.email = email;
        this.login = login;
        this.password = password;
    }

    public Compte() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword(String password) {
        return DigestUtils.shaHex(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
