package hu.vadavar.rija.models.boards;

import java.util.Arrays;
import java.util.Objects;

import hu.vadavar.rija.models.Status;

public class Board {

    private String id;
    private String team;
    private String name;
    private Status[] statuses;
    private String[] ticketIds;

    public Board() {}

    public Board(String id, String team, String name, Status[] statuses, String[] ticketIds) {
        this.id = id;
        this.team = team;
        this.name = name;
        this.statuses = statuses;
        this.ticketIds = ticketIds;
    }

    public Board(Board board) {
        this.id = board.id;
        this.team = board.team;
        this.name = board.name;
        this.statuses = board.statuses;
        this.ticketIds = board.ticketIds;
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

    public Status[] getStatuses() {
        return statuses;
    }

    public Board setStatuses(Status[] statuses) {
        this.statuses = statuses;
        return this;
    }

    public String[] getTicketIds() {
        return ticketIds;
    }

    public Board setTicketIds(String[] ticketIds) {
        this.ticketIds = ticketIds;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && Objects.equals(team, board.team) && Objects.equals(name, board.name) && Arrays.equals(statuses, board.statuses) && Arrays.equals(ticketIds, board.ticketIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, team, name);
        result = 31 * result + Arrays.hashCode(statuses);
        result = 31 * result + Arrays.hashCode(ticketIds);
        return result;
    }

    public static Board DEFAULT_BOARD(
            String[] stateIds,
            String teamId,
            String boardId
    ) {
        return new Board()
            .setId(boardId)
            .setName("Alapértelmezett tábla")
            .setStatuses(new Status[] {
                new Status(stateIds[0], "Új", "#FF0000", new String[]{}, new String[]{stateIds[1]}),
                new Status(stateIds[1], "Folyamatban", "#00FF00", new String[]{stateIds[0]}, new String[]{stateIds[2]}),
                new Status(stateIds[2], "Kész", "#0000FF", new String[]{stateIds[1]}, new String[]{})
            })
            .setTeam(teamId)
            .setTicketIds(new String[]{});
    }
}
