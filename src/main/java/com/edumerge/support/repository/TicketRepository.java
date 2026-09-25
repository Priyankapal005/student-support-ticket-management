package com.edumerge.support.repository;

	import com.edumerge.support.entity.Ticket;
	import com.edumerge.support.entity.TicketStatus;
	import com.edumerge.support.entity.Priority;
	import org.springframework.data.jpa.repository.JpaRepository;

	import java.util.List;

	public interface TicketRepository extends JpaRepository<Ticket, Long> {

	    List<Ticket> findByStatus(TicketStatus status);

	    List<Ticket> findByPriority(Priority priority);

	    List<Ticket> findByStudentId(Long studentId);

	    List<Ticket> findByAssignedToId(Long staffId);

	    List<Ticket> findByCategoryId(Long categoryId);

	    List<Ticket> findByStatusAndPriority(
	            TicketStatus status,
	            Priority priority
	    );

	    boolean existsByTicketNumber(String ticketNumber);
	}


