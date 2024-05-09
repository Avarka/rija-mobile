package hu.vadavar.rija.models.boards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import hu.vadavar.rija.models.Status;

public class Board {

    private String id;
    private String team;
    private String name;
    private List<Status> statuses;
    private List<String> tickets;

    public Board() {}

    public Board(String id, String team, String name, List<Status> statuses, List<String> tickets) {
        this.id = id;
        this.team = team;
        this.name = name;
        this.statuses = statuses;
        this.tickets = tickets;
    }

    public Board(Board board) {
        this.id = board.id;
        this.team = board.team;
        this.name = board.name;
        this.statuses = board.statuses;
        this.tickets = board.tickets;
    }

    public String getId() {
        return id;
    }

    public Board setId(String id) {
        this.id = id;
        return this;
    }

    public String getTeam() {
        return team;
    }

    public Board setTeam(String team) {
        this.team = team;
        return this;
    }

    public String getName() {
        return name;
    }

    public Board setName(String name) {
        this.name = name;
        return this;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public Board setStatuses(List<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public Board setTickets(List<String> tickets) {
        this.tickets = tickets;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && Objects.equals(team, board.team) && Objects.equals(name, board.name) && Objects.equals(statuses, board.statuses) && Objects.equals(tickets, board.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, name, statuses, tickets);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", team='" + team + '\'' +
                ", name='" + name + '\'' +
                ", statuses=" + statuses +
                ", tickets=" + tickets +
                '}';
    }

    public static Board DEFAULT_BOARD(
            String[] stateIds,
            String teamId,
            String boardId
    ) {
        return new Board()
            .setId(boardId)
            .setName("Alapértelmezett tábla")
            .setStatuses(
                Arrays.asList(
                    new Status(stateIds[0], "Új", "#FF0000", new ArrayList<>(), List.of(new String[]{stateIds[1]})),
                    new Status(stateIds[1], "Folyamatban", "#00FF00", List.of(new String[]{stateIds[0]}), List.of(new String[]{stateIds[2]})),
                    new Status(stateIds[2], "Kész", "#0000FF", List.of(new String[]{stateIds[1]}), new ArrayList<>())
                )
            )
            .setTeam(teamId)
            .setTickets(new ArrayList<>());
    }
}
