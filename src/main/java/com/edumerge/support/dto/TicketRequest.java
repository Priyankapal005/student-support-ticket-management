package com.edumerge.support.dto;


	import com.edumerge.support.entity.Priority;
	import jakarta.validation.constraints.NotBlank;
	import jakarta.validation.constraints.NotNull;

	public class TicketRequest {

	    @NotNull(message = "Student ID is required")
	    private Long studentId;

	    @NotNull(message = "Category ID is required")
	    private Long categoryId;

	    @NotBlank(message = "Subject is required")
	    private String subject;

	    @NotBlank(message = "Description is required")
	    private String description;

	    private Priority priority;

	    public Long getStudentId() {
	        return studentId;
	    }

	    public void setStudentId(Long studentId) {
	        this.studentId = studentId;
	    }

	    public Long getCategoryId() {
	        return categoryId;
	    }

	    public void setCategoryId(Long categoryId) {
	        this.categoryId = categoryId;
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
	}


