package com.example.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    @Getter
    private final UserRepository repository;
    private final PasswordHandler passwordHandler = new PasswordHandler();
    private final UserUpdater userUpdater = new UserUpdater();
    private final EmailService emailService = new EmailServiceImpl();

    @GetMapping("/secret")
    public String getSecret() {
        return "secret";
    }
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }
    @PostMapping("/register")
    public String newUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam int userGroupId, @RequestParam int countryId, @RequestParam String phone, @RequestParam String corporate, @RequestParam String title, @RequestParam String usageReason, @RequestParam String password, @RequestParam String rePassword) throws PasswordErrorException {
        List<myUser> liste = repository.findAll();
        for (com.example.login.myUser myuser : liste) {
            if (myuser.getEmail().equals(email)) {
                throw new PasswordErrorException("User already exists");
            }
        }
        repository.save(new myUser(name, surname, email, userGroupId, countryId, phone, corporate, title, usageReason, password, rePassword));
        return "login";
    }
    @GetMapping("/user")
    public String getUsers() {
        return "user";
    }
    @ModelAttribute("allUsers")
    public List<myUser> all() {
        return repository.findAll();
    }
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    @GetMapping("/login_success")
    public String getLoginSuccess() {
        return "login_success";
    }
    @GetMapping("/login_failure")
    public String getLoginFailure() {
        return "login_failure";
    }
    @GetMapping("/forgot")
    public String getForgot() {
        return "forgot";
    }
    @PostMapping("/forgot")
    public String sendForgot(@RequestParam String email) {
        emailService.sendSimpleMail(email);
        return "login";
    }
    @GetMapping("/main")
    public String getMain() {
        return "main";
    }
    @PostMapping("/main")
    public String auth(HttpServletRequest req, @RequestParam String email, @RequestParam String password, Model model) {
        List<myUser> liste = repository.findAll();
        for (com.example.login.myUser myUser : liste) {
            if (myUser.getEmail().equals(email) && !myUser.isActive())
                throw new UserNotFoundException();
            if (myUser.getEmail().equals(email) && new BCryptPasswordEncoder().matches(password, myUser.getPassword())) {
                AuthProvider athp = new AuthProvider(repository);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
                Authentication authentication = athp.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                model.addAttribute("user", myUser);
                req.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
                req.getSession(true).setAttribute("userdata", myUser);
                return "main";
            }
            if (myUser.getEmail().equals(email)) {
                throw new PasswordErrorException("Password Incorrect");
            }
        }
        throw new UserNotFoundException();
    }
    @GetMapping("/profile")
    public String getProfile(HttpServletRequest req, Model model) {
        myUser usr = (myUser) req.getSession().getAttribute("userdata");
        model.addAttribute("user", usr);
        return "profile";
    }
    @PostMapping("/profile")
    public String updateUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam int userGroupId, @RequestParam int countryId, @RequestParam String phone, @RequestParam String corporate, @RequestParam String title, @RequestParam String usageReason) {
        if (userUpdater.updateUser(name, surname, email, userGroupId, countryId, phone, corporate, title, usageReason, repository))
            return "user";
        throw new UserNotFoundException();
    }
    @GetMapping("/password")
    public String getPassword(HttpServletRequest req, Model model) {
        myUser usr = (myUser) req.getSession().getAttribute("userdata");
        model.addAttribute("user", usr);
        return "password";
    }
    @PostMapping("/password")
    public String  updatePassword(@RequestParam String emaill, @RequestParam String old, @RequestParam String neww, @RequestParam String newre) {
        return (passwordHandler.passwordChecker(emaill, old, neww, newre, repository));
    }
    @PostMapping("/delete")
    public String delete(HttpServletRequest req, HttpServletResponse res) {
        myUser usr = (myUser) req.getSession().getAttribute("userdata");
        usr.setActive(false);
        repository.save(usr);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, res, auth);
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse res) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, res, auth);
        }
        return "login";
    }
}
