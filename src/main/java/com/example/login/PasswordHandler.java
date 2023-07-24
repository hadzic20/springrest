package com.example.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class PasswordHandler {
    public String passwordChecker(String emaill, String old, String neww, String newre, UserRepository repository) {
        List<myUser> liste = repository.findAll();
        for (com.example.login.myUser myuser : liste) {
            if (myuser.getEmail().equals(emaill)) {
                myUser chosenone = repository.getReferenceById(myuser.getId());
                if (neww.equals(newre)) {
                    if ( new BCryptPasswordEncoder().matches(old, myuser.getPassword())) {
                        if (neww.contains(chosenone.getName()) || neww.contains(chosenone.getSurname())) {
                            throw new PasswordErrorException("Password can not contain name or surname!");
                        }
                        if (neww.length() < 10) {
                            throw new PasswordErrorException("Password is too short!");
                        }
                        if (!neww.contains("!") && !neww.contains("'") && !neww.contains("^") && !neww.contains("+") && !neww.contains("%") && !neww.contains("/") && !neww.contains("(") && !neww.contains(")") && !neww.contains("=") && !neww.contains("?") && !neww.contains("*") && !neww.contains("-")) {
                            throw new PasswordErrorException("Password must include special character!");
                        }
                        chosenone.setPassword(new BCryptPasswordEncoder().encode(neww));
                        repository.save(chosenone);
                        return "user";
                    }
                    else
                        throw new PasswordErrorException("Old password is incorrect!");
                }
                else
                    throw new PasswordErrorException("New passwords not matching!");
            }
        }
        throw new UserNotFoundException();
    }
}
