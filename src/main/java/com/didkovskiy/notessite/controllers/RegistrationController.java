package com.didkovskiy.notessite.controllers;

import com.didkovskiy.notessite.domains.userstore.RegistrationForm;
import com.didkovskiy.notessite.domains.userstore.User;
import com.didkovskiy.notessite.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository    = userRepository;
        this.passwordEncoder   = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registerForm";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm, Model model) {
        User userFromDB = userRepository.findByUsername(registrationForm.getUsername());
        if(userFromDB != null) {
            model.addAttribute("errorMsg", "User already exists!");
            return "registerForm";
        }
        userRepository.save(registrationForm.toUsualUser(passwordEncoder));
        return "redirect:/login";
    }

}