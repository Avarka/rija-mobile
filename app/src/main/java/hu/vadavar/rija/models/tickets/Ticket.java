package hu.vadavar.rija.models.tickets;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import hu.vadavar.rija.models.Comment;
import hu.vadavar.rija.models.Status;

public class Ticket {
    private String id;
    private String title;
    private String description;
    private Status status;
    private String assignee;
    private String reporter;
    private Date created;
    private Date updated;
    private List<Comment> comments;

    public Ticket() {}

    public Ticket(String id, String title, String description, Status status, String assignee, String reporter, Date created, Date updated, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.reporter = reporter;
        this.created = created;
        this.updated = updated;
        this.comments = comments;
    }

    public Ticket(Ticket ticket) {
        this.id = ticket.id;
        this.title = ticket.title;
        this.description = ticket.description;
        this.status = ticket.status;
        this.assignee = ticket.assignee;
        this.reporter = ticket.reporter;
        this.created = ticket.created;
        this.updated = ticket.updated;
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

    public Status getStatus() {
        return status;
    }

    public Ticket setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getAssigneeId() {
        return assignee;
    }

    public Ticket setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getReporterId() {
        return reporter;
    }

    public Ticket setReporter(String reporter) {
        this.reporter = reporter;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Ticket setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getUpdated() {
        return updated;
    }

    public Ticket setUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Ticket setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", statusId=" + status +
                ", assignee='" + assignee + '\'' +
                ", reporter='" + reporter + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(title, ticket.title) && Objects.equals(description, ticket.description) && Objects.equals(status, ticket.status) && Objects.equals(assignee, ticket.assignee) && Objects.equals(reporter, ticket.reporter) && Objects.equals(created, ticket.created) && Objects.equals(updated, ticket.updated) && Objects.equals(comments, ticket.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, assignee, reporter, created, updated, comments);
    }
}