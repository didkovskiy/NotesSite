package com.didkovskiy.shitsite.controllers;

import com.didkovskiy.shitsite.domains.Message;
import com.didkovskiy.shitsite.domains.userstore.User;
import com.didkovskiy.shitsite.repositories.FileRepository;
import com.didkovskiy.shitsite.repositories.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private final MessageRepository messageRepository;
    private final FileRepository fileRepository;

    public HomeController(MessageRepository messageRepository, FileRepository fileRepository) {
        this.messageRepository = messageRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping
    public String home(@ModelAttribute("message") Message message,
                       @AuthenticationPrincipal User user, Model model) {
        List<Message> all = messageRepository.findAll();
        model.addAttribute("messages", all);
        model.addAttribute("username", user.getUsername());
        return "home";
    }

    @PostMapping()
    public String newMsg(@ModelAttribute("message") Message message,
                         @AuthenticationPrincipal User user,
                         @RequestParam("file") MultipartFile file) {
        fileRepository.saveNewFile(file, message);
        message.setAuthor(user);
        messageRepository.save(message);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filterMsg(@ModelAttribute("message") Message message, Model model) {
        List<Message> filteredMsgs;
        if (!message.getTag().isEmpty() && message.getTag() != null)
            filteredMsgs = messageRepository.findAllByTag(message.getTag());
        else filteredMsgs = messageRepository.findAll();
        model.addAttribute("messages", filteredMsgs);
        return "home";
    }

    @PostMapping("/delete")
    public String deleteMsg(@ModelAttribute("message") Message message) {
        if(messageRepository.findById(message.getId()).isPresent())
            messageRepository.deleteById(message.getId());
        return "redirect:/";
    }
}
//ctrl + shift + f9 to reload code