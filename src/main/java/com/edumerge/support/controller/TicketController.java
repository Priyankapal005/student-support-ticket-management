package com.edumerge.support.controller;

import com.edumerge.support.dto.TicketRequest;
import com.edumerge.support.dto.TicketResponse;
import com.edumerge.support.entity.Priority;
import com.edumerge.support.entity.TicketStatus;
import com.edumerge.support.service.TicketService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // =========================================================
    // CREATE TICKET
    // =========================================================

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody TicketRequest request) {

        TicketResponse response =
                ticketService.createTicket(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================================================
    // GET TICKETS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long staffId) {

        // Filter by status
        if (status != null) {
            return ResponseEntity.ok(
                    ticketService.getTicketsByStatus(status)
            );
        }

        // Filter by priority
        if (priority != null) {
            return ResponseEntity.ok(
                    ticketService.getTicketsByPriority(priority)
            );
        }

        // Filter by category
        if (categoryId != null) {
            return ResponseEntity.ok(
                    ticketService.getTicketsByCategory(categoryId)
            );
        }

        // Filter by student
        if (studentId != null) {
            return ResponseEntity.ok(
                    ticketService.getTicketsByStudent(studentId)
            );
        }

        // Filter by assigned staff
        if (staffId != null) {
            return ResponseEntity.ok(
                    ticketService.getTicketsByStaff(staffId)
            );
        }

        // No filter → return all tickets
        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }

    // =========================================================
    // GET TICKET BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ticketService.getTicketById(id)
        );
    }

    // =========================================================
    // UPDATE TICKET
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequest request) {

        return ResponseEntity.ok(
                ticketService.updateTicket(id, request)
        );
    }

    // =========================================================
    // DELETE TICKET
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTicket(
            @PathVariable Long id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.ok(
                Map.of("message", "Ticket deleted successfully")
        );
    }

    // =========================================================
    // ASSIGN TICKET
    // =========================================================

    @PutMapping("/{id}/assign")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable Long id,
            @RequestParam Long staffId) {

        return ResponseEntity.ok(
                ticketService.assignTicket(id, staffId)
        );
    }

    // =========================================================
    // CHANGE STATUS
    // =========================================================

    @PutMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam TicketStatus status) {

        return ResponseEntity.ok(
                ticketService.updateStatus(id, status)
        );
    }

    // =========================================================
    // CHANGE PRIORITY
    // =========================================================

    @PutMapping("/{id}/priority")
    public ResponseEntity<TicketResponse> updatePriority(
            @PathVariable Long id,
            @RequestParam Priority priority) {

        return ResponseEntity.ok(
                ticketService.updatePriority(id, priority)
        );
    }

    // =========================================================
    // SEARCH TICKETS
    // =========================================================

    @GetMapping("/search")
    public ResponseEntity<List<TicketResponse>> searchTickets(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                ticketService.searchTickets(keyword)
        );
    }
}
