
	package com.edumerge.support.controller;

	import com.edumerge.support.entity.TicketComment;
	import com.edumerge.support.service.TicketCommentService;

	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;
	import java.util.Map;

	@RestController
	@RequestMapping("/api/tickets")
	public class TicketCommentController {

	    private final TicketCommentService ticketCommentService;

	    public TicketCommentController(
	            TicketCommentService ticketCommentService) {

	        this.ticketCommentService = ticketCommentService;
	    }

	    // ADD COMMENT
	    @PostMapping("/{ticketId}/comments")
	    public ResponseEntity<TicketComment> addComment(
	            @PathVariable Long ticketId,
	            @RequestParam Long userId,
	            @RequestBody Map<String, String> request) {

	        String commentText = request.get("comment");

	        TicketComment comment =
	                ticketCommentService.addComment(
	                        ticketId,
	                        userId,
	                        commentText);

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(comment);
	    }

	    // GET COMMENTS
	    @GetMapping("/{ticketId}/comments")
	    public ResponseEntity<List<TicketComment>> getComments(
	            @PathVariable Long ticketId) {

	        return ResponseEntity.ok(
	                ticketCommentService.getComments(ticketId)
	        );
	    }
	}


