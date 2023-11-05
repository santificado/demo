package br.com.fiap.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "auth/logout";
    }

    @GetMapping("/user")
    public String userPage(Model model){
        model.addAttribute("users", repository.findAll());
        return "auth/user";
    }

}