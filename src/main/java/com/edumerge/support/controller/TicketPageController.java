package com.edumerge.support.controller;

import com.edumerge.support.dto.TicketResponse;
import com.edumerge.support.entity.TicketComment;
import com.edumerge.support.entity.TicketHistory;
import com.edumerge.support.service.TicketCommentService;
import com.edumerge.support.service.TicketService;
import com.edumerge.support.repository.TicketHistoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TicketPageController {

    private final TicketService ticketService;
    private final TicketCommentService ticketCommentService;
    private final TicketHistoryRepository ticketHistoryRepository;

    public TicketPageController(
            TicketService ticketService,
            TicketCommentService ticketCommentService,
            TicketHistoryRepository ticketHistoryRepository) {

        this.ticketService = ticketService;
        this.ticketCommentService = ticketCommentService;
        this.ticketHistoryRepository = ticketHistoryRepository;
    }

    // ============================
    // Ticket List Page
    // ============================

    @GetMapping("/tickets")
    public String tickets(Model model) {

        model.addAttribute(
                "tickets",
                ticketService.getAllTickets()
        );

        return "tickets";
    }


    // ============================
    // Ticket Details Page
    // ============================

    @GetMapping("/tickets/{id}")
    public String ticketDetails(
            @PathVariable Long id,
            Model model) {

        TicketResponse ticket =
                ticketService.getTicketById(id);

        List<TicketComment> comments =
                ticketCommentService.getComments(id);

        List<TicketHistory> history =
                ticketHistoryRepository
                        .findByTicketIdOrderByCreatedAtDesc(id);

        model.addAttribute("ticket", ticket);

        model.addAttribute("comments", comments);

        model.addAttribute("history", history);

        return "ticket-details";
    }


    // ============================
    // Create Ticket Page
    // ============================

    @GetMapping("/tickets/create")
    public String createTicketForm(Model model) {

        model.addAttribute(
                "categories",
                ticketService.getActiveCategories()
        );

        return "create-ticket";
    }
}