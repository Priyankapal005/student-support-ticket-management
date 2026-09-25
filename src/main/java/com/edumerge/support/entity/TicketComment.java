package com.edumerge.support.entity;

	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "ticket_comments")
	public class TicketComment {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ticket_id", nullable = false)
	    private Ticket ticket;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @Column(nullable = false, columnDefinition = "TEXT")
	    private String comment;

	    private LocalDateTime createdAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }

	    public TicketComment() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Ticket getTicket() {
	        return ticket;
	    }

	    public void setTicket(Ticket ticket) {
	        this.ticket = ticket;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public String getComment() {
	        return comment;
	    }

	    public void setComment(String comment) {
	        this.comment = comment;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }
	}


