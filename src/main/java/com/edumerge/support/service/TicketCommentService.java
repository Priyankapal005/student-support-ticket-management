package com.edumerge.support.service;



	import com.edumerge.support.entity.Ticket;
	import com.edumerge.support.entity.TicketComment;
	import com.edumerge.support.entity.User;
	import com.edumerge.support.repository.TicketCommentRepository;
	import com.edumerge.support.repository.TicketRepository;
	import com.edumerge.support.repository.UserRepository;

	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

	import java.util.List;

	@Service
	public class TicketCommentService {

	    private final TicketCommentRepository ticketCommentRepository;
	    private final TicketRepository ticketRepository;
	    private final UserRepository userRepository;

	    public TicketCommentService(
	            TicketCommentRepository ticketCommentRepository,
	            TicketRepository ticketRepository,
	            UserRepository userRepository) {

	        this.ticketCommentRepository = ticketCommentRepository;
	        this.ticketRepository = ticketRepository;
	        this.userRepository = userRepository;
	    }

	    // =========================================================
	    // ADD COMMENT
	    // =========================================================

	    @Transactional
	    public TicketComment addComment(
	            Long ticketId,
	            Long userId,
	            String commentText) {

	        Ticket ticket = ticketRepository.findById(ticketId)
	                .orElseThrow(() ->
	                        new RuntimeException("Ticket not found"));

	        User user = userRepository.findById(userId)
	                .orElseThrow(() ->
	                        new RuntimeException("User not found"));

	        if (commentText == null ||
	                commentText.trim().isEmpty()) {

	            throw new RuntimeException(
	                    "Comment cannot be empty");
	        }

	        TicketComment comment = new TicketComment();

	        comment.setTicket(ticket);
	        comment.setUser(user);
	        comment.setComment(commentText.trim());

	        return ticketCommentRepository.save(comment);
	    }

	    // =========================================================
	    // GET COMMENTS FOR TICKET
	    // =========================================================

	    @Transactional(readOnly = true)
	    public List<TicketComment> getComments(Long ticketId) {

	        if (!ticketRepository.existsById(ticketId)) {
	            throw new RuntimeException(
	                    "Ticket not found");
	        }

	        return ticketCommentRepository
	                .findByTicketIdOrderByCreatedAtAsc(ticketId);
	    }
	}


