
package com.edumerge.support.service;

import com.edumerge.support.dto.TicketRequest;
import com.edumerge.support.dto.TicketResponse;
import com.edumerge.support.entity.Category;
import com.edumerge.support.entity.Priority;
import com.edumerge.support.entity.Role;
import com.edumerge.support.entity.Ticket;
import com.edumerge.support.entity.TicketHistory;
import com.edumerge.support.entity.TicketStatus;
import com.edumerge.support.entity.User;
import com.edumerge.support.repository.CategoryRepository;
import com.edumerge.support.repository.TicketHistoryRepository;
import com.edumerge.support.repository.TicketRepository;
import com.edumerge.support.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TicketHistoryRepository ticketHistoryRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            TicketHistoryRepository ticketHistoryRepository) {

        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
    }


    // ==========================================
    // CREATE TICKET
    // ==========================================

    @Transactional
    public TicketResponse createTicket(TicketRequest request) {

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException(
                    "Selected user is not a student");
        }

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        if (!category.isActive()) {
            throw new RuntimeException(
                    "Selected category is inactive");
        }

        Ticket ticket = new Ticket();

        ticket.setTicketNumber(generateTicketNumber());

        ticket.setStudent(student);

        ticket.setCategory(category);

        ticket.setSubject(request.getSubject());

        ticket.setDescription(request.getDescription());

        if (request.getPriority() != null) {

            ticket.setPriority(request.getPriority());

        } else {

            ticket.setPriority(
                    category.getDefaultPriority()
            );
        }

        ticket.setStatus(TicketStatus.OPEN);

        ticket.setDueAt(
                LocalDateTime.now()
                        .plusHours(category.getSlaHours())
        );

        Ticket savedTicket =
                ticketRepository.save(ticket);

        return convertToResponse(savedTicket);
    }


    // ==========================================
    // GET ACTIVE CATEGORIES
    // ==========================================

    @Transactional(readOnly = true)
    public List<Category> getActiveCategories() {

        return categoryRepository.findByActiveTrue();
    }


    // ==========================================
    // GET ALL TICKETS
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // GET TICKET BY ID
    // ==========================================

    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        return convertToResponse(ticket);
    }


    // ==========================================
    // UPDATE TICKET
    // ==========================================

    @Transactional
    public TicketResponse updateTicket(
            Long id,
            TicketRequest request) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.CLOSED ||
                ticket.getStatus() == TicketStatus.CANCELLED) {

            throw new RuntimeException(
                    "Closed or cancelled ticket cannot be updated");
        }

        User student = userRepository.findById(
                request.getStudentId()
        ).orElseThrow(() ->
                new RuntimeException("Student not found"));

        if (student.getRole() != Role.STUDENT) {

            throw new RuntimeException(
                    "Selected user is not a student");
        }

        Category category = categoryRepository.findById(
                request.getCategoryId()
        ).orElseThrow(() ->
                new RuntimeException("Category not found"));

        if (!category.isActive()) {

            throw new RuntimeException(
                    "Selected category is inactive");
        }

        ticket.setStudent(student);

        ticket.setCategory(category);

        ticket.setSubject(request.getSubject());

        ticket.setDescription(request.getDescription());

        if (request.getPriority() != null) {

            ticket.setPriority(
                    request.getPriority()
            );
        }

        Ticket updatedTicket =
                ticketRepository.save(ticket);

        return convertToResponse(updatedTicket);
    }


    // ==========================================
    // DELETE TICKET
    // ==========================================

    @Transactional
    public void deleteTicket(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        if (ticket.getStatus() != TicketStatus.OPEN) {

            throw new RuntimeException(
                    "Only OPEN tickets can be deleted");
        }

        ticketRepository.delete(ticket);
    }


    // ==========================================
    // ASSIGN TICKET
    // ==========================================

    @Transactional
    public TicketResponse assignTicket(
            Long ticketId,
            Long staffId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        User staff = userRepository.findById(staffId)
                .orElseThrow(() ->
                        new RuntimeException("Staff user not found"));

        if (staff.getRole() != Role.STAFF) {

            throw new RuntimeException(
                    "Selected user is not a staff member");
        }

        if (!staff.isActive()) {

            throw new RuntimeException(
                    "Selected staff member is inactive");
        }

        if (ticket.getStatus() == TicketStatus.CLOSED ||
                ticket.getStatus() == TicketStatus.CANCELLED) {

            throw new RuntimeException(
                    "Closed or cancelled ticket cannot be assigned");
        }

        String oldStaff =
                ticket.getAssignedTo() == null
                        ? null
                        : ticket.getAssignedTo().getName();

        ticket.setAssignedTo(staff);

        ticket.setStatus(TicketStatus.ASSIGNED);

        Ticket savedTicket =
                ticketRepository.save(ticket);

        saveHistory(
                savedTicket,
                "ASSIGNED",
                oldStaff,
                staff.getName(),
                ticket.getStudent()
        );

        return convertToResponse(savedTicket);
    }


    // ==========================================
    // UPDATE STATUS
    // ==========================================

    @Transactional
    public TicketResponse updateStatus(
            Long ticketId,
            TicketStatus newStatus) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        if (newStatus == null) {

            throw new RuntimeException(
                    "Status is required");
        }

        if (ticket.getStatus() == TicketStatus.CLOSED ||
                ticket.getStatus() == TicketStatus.CANCELLED) {

            throw new RuntimeException(
                    "Closed or cancelled ticket cannot be changed");
        }

        TicketStatus oldStatus =
                ticket.getStatus();

        ticket.setStatus(newStatus);

        LocalDateTime now =
                LocalDateTime.now();

        if (newStatus == TicketStatus.RESOLVED) {

            ticket.setResolvedAt(now);
        }

        if (newStatus == TicketStatus.CLOSED) {

            ticket.setClosedAt(now);
        }

        Ticket savedTicket =
                ticketRepository.save(ticket);

        saveHistory(
                savedTicket,
                "STATUS_CHANGED",
                oldStatus.toString(),
                newStatus.toString(),
                ticket.getStudent()
        );

        return convertToResponse(savedTicket);
    }


    // ==========================================
    // UPDATE PRIORITY
    // ==========================================

    @Transactional
    public TicketResponse updatePriority(
            Long ticketId,
            Priority newPriority) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        if (newPriority == null) {

            throw new RuntimeException(
                    "Priority is required");
        }

        if (ticket.getStatus() == TicketStatus.CLOSED ||
                ticket.getStatus() == TicketStatus.CANCELLED) {

            throw new RuntimeException(
                    "Closed or cancelled ticket priority cannot be changed");
        }

        Priority oldPriority =
                ticket.getPriority();

        ticket.setPriority(newPriority);

        Ticket savedTicket =
                ticketRepository.save(ticket);

        saveHistory(
                savedTicket,
                "PRIORITY_CHANGED",
                oldPriority == null
                        ? null
                        : oldPriority.toString(),
                newPriority.toString(),
                ticket.getStudent()
        );

        return convertToResponse(savedTicket);
    }


    // ==========================================
    // SEARCH TICKETS
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> searchTickets(
            String keyword) {

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return getAllTickets();
        }

        String search =
                keyword.toLowerCase();

        return ticketRepository.findAll()
                .stream()
                .filter(ticket ->
                        ticket.getSubject()
                                .toLowerCase()
                                .contains(search)
                        ||
                        ticket.getDescription()
                                .toLowerCase()
                                .contains(search)
                )
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // FILTER BY STATUS
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByStatus(
            TicketStatus status) {

        return ticketRepository.findByStatus(status)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // FILTER BY PRIORITY
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByPriority(
            Priority priority) {

        return ticketRepository.findByPriority(priority)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // FILTER BY STUDENT
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByStudent(
            Long studentId) {

        return ticketRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // FILTER BY STAFF
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByStaff(
            Long staffId) {

        return ticketRepository
                .findByAssignedToId(staffId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // FILTER BY CATEGORY
    // ==========================================

    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByCategory(
            Long categoryId) {

        return ticketRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // SAVE HISTORY
    // ==========================================

    private void saveHistory(
            Ticket ticket,
            String action,
            String oldValue,
            String newValue,
            User changedBy) {

        TicketHistory history =
                new TicketHistory();

        history.setTicket(ticket);

        history.setAction(action);

        history.setOldValue(oldValue);

        history.setNewValue(newValue);

        history.setChangedBy(changedBy);

        ticketHistoryRepository.save(history);
    }


    // ==========================================
    // GENERATE TICKET NUMBER
    // ==========================================

    private String generateTicketNumber() {

        String ticketNumber;

        do {

            long number =
                    System.currentTimeMillis();

            ticketNumber =
                    "TKT-" + number;

        } while (
                ticketRepository
                        .existsByTicketNumber(ticketNumber)
        );

        return ticketNumber;
    }


    // ==========================================
    // ENTITY → RESPONSE
    // ==========================================

    private TicketResponse convertToResponse(
            Ticket ticket) {

        TicketResponse response =
                new TicketResponse();

        response.setId(ticket.getId());

        response.setTicketNumber(
                ticket.getTicketNumber()
        );

        response.setStudentId(
                ticket.getStudent().getId()
        );

        response.setStudentName(
                ticket.getStudent().getName()
        );

        response.setCategoryId(
                ticket.getCategory().getId()
        );

        response.setCategoryName(
                ticket.getCategory().getName()
        );

        if (ticket.getAssignedTo() != null) {

            response.setAssignedToId(
                    ticket.getAssignedTo().getId()
            );

            response.setAssignedToName(
                    ticket.getAssignedTo().getName()
            );
        }

        response.setSubject(
                ticket.getSubject()
        );

        response.setDescription(
                ticket.getDescription()
        );

        response.setPriority(
                ticket.getPriority()
        );

        response.setStatus(
                ticket.getStatus()
        );

        response.setCreatedAt(
                ticket.getCreatedAt()
        );

        response.setUpdatedAt(
                ticket.getUpdatedAt()
        );

        response.setDueAt(
                ticket.getDueAt()
        );

        response.setResolvedAt(
                ticket.getResolvedAt()
        );

        response.setClosedAt(
                ticket.getClosedAt()
        );

        return response;
    }
}