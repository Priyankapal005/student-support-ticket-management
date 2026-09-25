
	package com.edumerge.support.controller;

	import com.edumerge.support.entity.TicketHistory;
	import com.edumerge.support.repository.TicketHistoryRepository;

	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/api/tickets")
	public class TicketHistoryController {

	    private final TicketHistoryRepository ticketHistoryRepository;

	    public TicketHistoryController(
	            TicketHistoryRepository ticketHistoryRepository) {

	        this.ticketHistoryRepository = ticketHistoryRepository;
	    }

	    // GET TICKET HISTORY
	    @GetMapping("/{ticketId}/history")
	    public ResponseEntity<List<TicketHistory>> getTicketHistory(
	            @PathVariable Long ticketId) {

	        return ResponseEntity.ok(
	                ticketHistoryRepository
	                        .findByTicketIdOrderByCreatedAtDesc(ticketId)
	        );
	    }
	}

