package com.edumerge.support.controller;

import com.edumerge.support.entity.User;
import com.edumerge.support.repository.UserRepository;
import com.edumerge.support.service.TicketService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RolePageController {

    private final UserRepository userRepository;
    private final TicketService ticketService;

    public RolePageController(UserRepository userRepository,
                              TicketService ticketService) {
        this.userRepository = userRepository;
        this.ticketService = ticketService;
    }

    // STUDENT
    @GetMapping("/student/home")
    public String studentHome() {
        return "student-home";
    }

    @GetMapping("/student/tickets")
    public String studentTickets(Model model,
                                 Authentication authentication) {

        User student = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("tickets",
                ticketService.getTicketsByStudent(student.getId()));

        model.addAttribute("student", student);

        return "student-tickets";
    }

    // STAFF
    @GetMapping("/staff/home")
    public String staffHome() {
        return "staff-home";
    }

    @GetMapping("/staff/tickets")
    public String staffTickets(Model model,
                               Authentication authentication) {

        User staff = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Staff user not found"));

        model.addAttribute("tickets",
                ticketService.getTicketsByStaff(staff.getId()));

        model.addAttribute("staff", staff);

        return "staff-tickets";
    }

    // ADMIN
    @GetMapping("/admin/home")
    public String adminHome(Model model) {

        model.addAttribute("tickets",
                ticketService.getAllTickets());

        return "admin-home";
    }
}