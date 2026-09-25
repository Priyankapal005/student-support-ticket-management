package com.edumerge.support.entity;


	import jakarta.persistence.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "ticket_categories")
	public class Category {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String name;

	    private String description;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private Priority defaultPriority = Priority.MEDIUM;

	    @Column(nullable = false)
	    private Integer slaHours = 24;

	    @Column(nullable = false)
	    private boolean active = true;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }

	    public Category() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Priority getDefaultPriority() {
	        return defaultPriority;
	    }

	    public void setDefaultPriority(Priority defaultPriority) {
	        this.defaultPriority = defaultPriority;
	    }

	    public Integer getSlaHours() {
	        return slaHours;
	    }

	    public void setSlaHours(Integer slaHours) {
	        this.slaHours = slaHours;
	    }

	    public boolean isActive() {
	        return active;
	    }

	    public void setActive(boolean active) {
	        this.active = active;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }
	}


