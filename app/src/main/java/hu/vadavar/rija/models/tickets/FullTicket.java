package hu.vadavar.rija.models.tickets;

import java.util.Date;
import java.util.Objects;

import hu.vadavar.rija.models.Comment;
import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.users.User;

public class FullTicket extends Ticket {
    private User assignee;
    private User reporter;

    public FullTicket() {
        super();
    }

    public FullTicket(Ticket ticket, User assignee, User reporter) {
        super(ticket);
        this.assignee = assignee;
        this.reporter = reporter;
    }

    public FullTicket(String id, String title, String description, Status statusId, Date createdAt, Date updatedAt, Comment[] comments, User assignee, User reporter) {
        super(id, title, description, statusId, assignee.getId(), reporter.getId(), createdAt, updatedAt, comments);
        this.assignee = assignee;
        this.reporter = reporter;
    }

    public User getAssignee() {
        return assignee;
    }

    public FullTicket setAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public User getReporter() {
        return reporter;
    }

    public FullTicket setReporter(User reporter) {
        this.reporter = reporter;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullTicket that = (FullTicket) o;
        return Objects.equals(assignee, that.assignee) && Objects.equals(reporter, that.reporter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), assignee, reporter);
    }
}
