package hu.vadavar.rija.models.boards;

import java.util.List;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.Status;
import hu.vadavar.rija.models.tickets.Ticket;

public class FullBoard extends Board {
    private List<Ticket> fTickets;

    public FullBoard() {
        super();
    }

    public FullBoard(Board board, List<Ticket> fTickets) {
        super(board);
        this.fTickets = fTickets;
    }

    public FullBoard(String id, String team, String name, List<Status> statuses, List<Ticket> fTickets) {
        super(id, team, name, statuses, fTickets.stream().map(Ticket::getId).collect(Collectors.toList()));
        this.fTickets = fTickets;
    }

    public List<Ticket> getfTickets() {
        return fTickets;
    }

    public FullBoard setfTickets(List<Ticket> fTickets) {
        this.fTickets = fTickets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullBoard fullBoard = (FullBoard) o;
        return fTickets.equals(fullBoard.fTickets);
    }

    @Override
    public int hashCode() {
        return fTickets.hashCode();
    }
}