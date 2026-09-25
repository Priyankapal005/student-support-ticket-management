package com.edumerge.support.entity;

	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "tickets")
	public class Ticket {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String ticketNumber;

	    // Student who created the ticket
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "student_id", nullable = false)
	    private User student;

	    // Ticket category
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "category_id", nullable = false)
	    private Category category;

	    // Staff assigned to handle the ticket
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "assigned_to")
	    private User assignedTo;

	    @Column(nullable = false)
	    private String subject;

	    @Column(nullable = false, columnDefinition = "TEXT")
	    private String description;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private Priority priority = Priority.MEDIUM;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private TicketStatus status = TicketStatus.OPEN;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

	    private LocalDateTime dueAt;

	    private LocalDateTime resolvedAt;

	    private LocalDateTime closedAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }

	    public Ticket() {
	    }

	    // Getters and Setters

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTicketNumber() {
	        return ticketNumber;
	    }

	    public void setTicketNumber(String ticketNumber) {
	        this.ticketNumber = ticketNumber;
	    }

	    public User getStudent() {
	        return student;
	    }

	    public void setStudent(User student) {
	        this.student = student;
	    }

	    public Category getCategory() {
	        return category;
	    }

	    public void setCategory(Category category) {
	        this.category = category;
	    }

	    public User getAssignedTo() {
	        return assignedTo;
	    }

	    public void setAssignedTo(User assignedTo) {
	        this.assignedTo = assignedTo;
	    }

	    public String getSubject() {
	        return subject;
	    }

	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Priority getPriority() {
	        return priority;
	    }

	    public void setPriority(Priority priority) {
	        this.priority = priority;
	    }

	    public TicketStatus getStatus() {
	        return status;
	    }

	    public void setStatus(TicketStatus status) {
	        this.status = status;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }

	    public LocalDateTime getDueAt() {
	        return dueAt;
	    }

	    public void setDueAt(LocalDateTime dueAt) {
	        this.dueAt = dueAt;
	    }

	    public LocalDateTime getResolvedAt() {
	        return resolvedAt;
	    }

	    public void setResolvedAt(LocalDateTime resolvedAt) {
	        this.resolvedAt = resolvedAt;
	    }

	    public LocalDateTime getClosedAt() {
	        return closedAt;
	    }

	    public void setClosedAt(LocalDateTime closedAt) {
	        this.closedAt = closedAt;
	    }
	}


