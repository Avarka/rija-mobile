package hu.vadavar.rija.models.tickets;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import hu.vadavar.rija.models.Comment;
import hu.vadavar.rija.models.Status;

public class Ticket {
    private String id;
    private String title;
    private String description;
    private Status statusId;
    private String assigneeId;
    private String reporterId;
    private Date createdAt;
    private Date updatedAt;
    private Comment[] comments;

    public Ticket() {}

    public Ticket(String id, String title, String description, Status statusId, String assigneeId, String reporterId, Date createdAt, Date updatedAt, Comment[] comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.assigneeId = assigneeId;
        this.reporterId = reporterId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
    }

    public Ticket(Ticket ticket) {
        this.id = ticket.id;
        this.title = ticket.title;
        this.description = ticket.description;
        this.statusId = ticket.statusId;
        this.assigneeId = ticket.assigneeId;
        this.reporterId = ticket.reporterId;
        this.createdAt = ticket.createdAt;
        this.updatedAt = ticket.updatedAt;
        this.comments = ticket.comments;
    }

    public String getId() {
        return id;
    }

    public Ticket setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Ticket setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Ticket setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatusId() {
        return statusId;
    }

    public Ticket setStatusId(Status statusId) {
        this.statusId = statusId;
        return this;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public Ticket setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
        return this;
    }

    public String getReporterId() {
        return reporterId;
    }

    public Ticket setReporterId(String reporterId) {
        this.reporterId = reporterId;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Ticket setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Ticket setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Comment[] getComments() {
        return comments;
    }

    public Ticket setComments(Comment[] comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(title, ticket.title) && Objects.equals(description, ticket.description) && Objects.equals(statusId, ticket.statusId) && Objects.equals(assigneeId, ticket.assigneeId) && Objects.equals(reporterId, ticket.reporterId) && Objects.equals(createdAt, ticket.createdAt) && Objects.equals(updatedAt, ticket.updatedAt) && Arrays.equals(comments, ticket.comments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, statusId, assigneeId, reporterId, createdAt, updatedAt);
        result = 31 * result + Arrays.hashCode(comments);
        return result;
    }
}
