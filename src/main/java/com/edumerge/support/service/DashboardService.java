package com.edumerge.support.service;


	import com.edumerge.support.dto.DashboardResponse;
	import com.edumerge.support.entity.Priority;
	import com.edumerge.support.entity.Ticket;
	import com.edumerge.support.entity.TicketStatus;
	import com.edumerge.support.repository.TicketRepository;

	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

	import java.util.List;

	@Service
	public class DashboardService {

	    private final TicketRepository ticketRepository;

	    public DashboardService(TicketRepository ticketRepository) {
	        this.ticketRepository = ticketRepository;
	    }

	    @Transactional(readOnly = true)
	    public DashboardResponse getDashboard() {

	        List<Ticket> tickets = ticketRepository.findAll();

	        DashboardResponse response = new DashboardResponse();

	        response.setTotalTickets(tickets.size());

	        response.setOpenTickets(
	                countByStatus(tickets, TicketStatus.OPEN)
	        );

	        response.setAssignedTickets(
	                countByStatus(tickets, TicketStatus.ASSIGNED)
	        );

	        response.setInProgressTickets(
	                countByStatus(tickets, TicketStatus.IN_PROGRESS)
	        );

	        response.setPendingTickets(
	                countByStatus(tickets, TicketStatus.PENDING)
	        );

	        response.setResolvedTickets(
	                countByStatus(tickets, TicketStatus.RESOLVED)
	        );

	        response.setClosedTickets(
	                countByStatus(tickets, TicketStatus.CLOSED)
	        );

	        response.setCancelledTickets(
	                countByStatus(tickets, TicketStatus.CANCELLED)
	        );

	        response.setHighPriorityTickets(
	                countByPriority(tickets, Priority.HIGH)
	        );

	        response.setCriticalPriorityTickets(
	                countByPriority(tickets, Priority.CRITICAL)
	        );

	        return response;
	    }

	    private long countByStatus(
	            List<Ticket> tickets,
	            TicketStatus status) {

	        return tickets.stream()
	                .filter(ticket -> ticket.getStatus() == status)
	                .count();
	    }

	    private long countByPriority(
	            List<Ticket> tickets,
	            Priority priority) {

	        return tickets.stream()
	                .filter(ticket -> ticket.getPriority() == priority)
	                .count();
	    }
	}


