package com.edumerge.support.entity;


	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "ticket_history")
	public class TicketHistory {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "ticket_id", nullable = false)
	    private Ticket ticket;

	    @Column(nullable = false)
	    private String action;

	    private String oldValue;

	    private String newValue;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "changed_by", nullable = false)
	    private User changedBy;

	    private LocalDateTime createdAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }

	    public TicketHistory() {
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

	    public String getAction() {
	        return action;
	    }

	    public void setAction(String action) {
	        this.action = action;
	    }

	    public String getOldValue() {
	        return oldValue;
	    }

	    public void setOldValue(String oldValue) {
	        this.oldValue = oldValue;
	    }

	    public String getNewValue() {
	        return newValue;
	    }

	    public void setNewValue(String newValue) {
	        this.newValue = newValue;
	    }

	    public User getChangedBy() {
	        return changedBy;
	    }

	    public void setChangedBy(User changedBy) {
	        this.changedBy = changedBy;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }
	}


