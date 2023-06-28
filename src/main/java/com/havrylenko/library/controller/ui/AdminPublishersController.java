package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.entity.Publisher;
import com.havrylenko.library.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/publishers")
public class AdminPublishersController {

    private final PublisherService publisherService;

    public AdminPublishersController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getAll(Model model) {
        var list = publisherService.getAll();
        model.addAttribute("publishers", list);
        model.addAttribute("newPublisher", new Publisher());
        return "admin/publisher/admin_publishers";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("newPublisher") Publisher publisher) {
        publisherService.save(publisher);
        return "redirect:/admin/publishers";
    }

    @PostMapping("{id}/delete")
    public String remove(@PathVariable String id) {
        publisherService.deleteById(id);
        return "redirect:/admin/publishers";
    }
}
