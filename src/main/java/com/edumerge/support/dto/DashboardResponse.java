package com.edumerge.support.dto;


	public class DashboardResponse {

	    private long totalTickets;
	    private long openTickets;
	    private long assignedTickets;
	    private long inProgressTickets;
	    private long pendingTickets;
	    private long resolvedTickets;
	    private long closedTickets;
	    private long cancelledTickets;
	    private long highPriorityTickets;
	    private long criticalPriorityTickets;

	    public DashboardResponse() {
	    }

	    public long getTotalTickets() {
	        return totalTickets;
	    }

	    public void setTotalTickets(long totalTickets) {
	        this.totalTickets = totalTickets;
	    }

	    public long getOpenTickets() {
	        return openTickets;
	    }

	    public void setOpenTickets(long openTickets) {
	        this.openTickets = openTickets;
	    }

	    public long getAssignedTickets() {
	        return assignedTickets;
	    }

	    public void setAssignedTickets(long assignedTickets) {
	        this.assignedTickets = assignedTickets;
	    }

	    public long getInProgressTickets() {
	        return inProgressTickets;
	    }

	    public void setInProgressTickets(long inProgressTickets) {
	        this.inProgressTickets = inProgressTickets;
	    }

	    public long getPendingTickets() {
	        return pendingTickets;
	    }

	    public void setPendingTickets(long pendingTickets) {
	        this.pendingTickets = pendingTickets;
	    }

	    public long getResolvedTickets() {
	        return resolvedTickets;
	    }

	    public void setResolvedTickets(long resolvedTickets) {
	        this.resolvedTickets = resolvedTickets;
	    }

	    public long getClosedTickets() {
	        return closedTickets;
	    }

	    public void setClosedTickets(long closedTickets) {
	        this.closedTickets = closedTickets;
	    }

	    public long getCancelledTickets() {
	        return cancelledTickets;
	    }

	    public void setCancelledTickets(long cancelledTickets) {
	        this.cancelledTickets = cancelledTickets;
	    }

	    public long getHighPriorityTickets() {
	        return highPriorityTickets;
	    }

	    public void setHighPriorityTickets(long highPriorityTickets) {
	        this.highPriorityTickets = highPriorityTickets;
	    }

	    public long getCriticalPriorityTickets() {
	        return criticalPriorityTickets;
	    }

	    public void setCriticalPriorityTickets(long criticalPriorityTickets) {
	        this.criticalPriorityTickets = criticalPriorityTickets;
	    }
	}


