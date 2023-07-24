package com.example.login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Data
public class myUser {
    @Column @Id @GeneratedValue private Long id;
    @Column(nullable = false, length = 64) private String name;
    @Column(nullable = false, length = 64) private String surname;
    @Column(nullable = false, length = 128) private String email;
    @Column(nullable = false) private int userGroupId;
    @Column private int countryId;
    @Column(length = 15) private String phone;
    @Column private String corporate;
    @Column private String title;
    @Column private String usageReason;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private boolean active;

    public myUser() {}
    public myUser(String name, String surname, String email, int userGroupId, int countryId, String phone, String corporate, String title, String usageReason, @NonNull String password, String rePassword) {
        if (!password.equals(rePassword)) {
            throw new PasswordErrorException("Passwords not matching!");
        }
        if (password.contains(name) || password.contains(surname)) {
            throw new PasswordErrorException("Password can not contain name or surname!");
        }
        if (password.length() < 10) {
            throw new PasswordErrorException("Password is too short!");
        }
        if (!password.contains("!") && !password.contains("'") && !password.contains("^") && !password.contains("+") && !password.contains("%") && !password.contains("/") && !password.contains("(") && !password.contains(")") && !password.contains("=") && !password.contains("?") && !password.contains("*") && !password.contains("-")) {
            throw new PasswordErrorException("Password must include special character!");
        }
        this.password = new BCryptPasswordEncoder().encode(password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userGroupId = userGroupId;
        this.countryId = countryId;
        this.phone = phone;
        this.corporate = corporate;
        this.title = title;
        this.usageReason = usageReason;
        this.active = true;
    }
}
