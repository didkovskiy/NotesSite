package com.didkovskiy.shitsite.controllers;

import com.didkovskiy.shitsite.domains.userstore.Role;
import com.didkovskiy.shitsite.domains.userstore.Status;
import com.didkovskiy.shitsite.domains.userstore.User;
import com.didkovskiy.shitsite.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/users")
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/main";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user",     userRepository.findById(id).orElseThrow());
        model.addAttribute("roles",    Role.values());
        model.addAttribute("statuses", Status.values());
        return "users/editForm";
    }

    @PostMapping("/{id}")
    public String save(@PathVariable Long id, @ModelAttribute User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(updatedUser.getRole());
        user.setStatus(updatedUser.getStatus());
        userRepository.save(user);
        return "redirect:/users";
    }

}
