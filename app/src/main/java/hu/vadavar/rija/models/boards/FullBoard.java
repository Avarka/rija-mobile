package hu.vadavar.rija.models.boards;

import java.util.Arrays;

import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.tickets.Ticket;

public class FullBoard extends Board {
    private Ticket[] tickets;

    public FullBoard() {
        super();
    }

    public FullBoard(Board board, Ticket[] tickets) {
        super(board);
        this.tickets = tickets;
    }

    public FullBoard(String id, String team, String name, Status[] statuses, Ticket[] tickets) {
        super(id, team, name, statuses, Arrays.stream(tickets).map(Ticket::getId).toArray(String[]::new));
        this.tickets = tickets;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public FullBoard setTickets(Ticket[] tickets) {
        this.tickets = tickets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullBoard fullBoard = (FullBoard) o;
        return Arrays.equals(tickets, fullBoard.tickets);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tickets);
    }
}
