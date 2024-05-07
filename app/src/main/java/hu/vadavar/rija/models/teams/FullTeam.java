package hu.vadavar.rija.models.teams;

import java.util.Arrays;

import hu.vadavar.rija.models.boards.FullBoard;
import hu.vadavar.rija.models.users.User;

public class FullTeam extends Team {
    private User[] members;
    private FullBoard[] boards;

    public FullTeam() {
        super();
    }

    public FullTeam(String id, String name, User[] members, FullBoard[] boards) {
        super(id, name, Arrays.stream(members).map(User::getId).toArray(String[]::new), Arrays.stream(boards).map(FullBoard::getId).toArray(String[]::new));
        this.members = members;
        this.boards = boards;
    }

    public FullTeam(Team team, User[] members, FullBoard[] boards) {
        super(team);
        this.members = members;
        this.boards = boards;
    }

    public User[] getMembers() {
        return members;
    }

    public FullTeam setMembers(User[] members) {
        this.members = members;
        return this;
    }

    public FullBoard[] getBoards() {
        return boards;
    }

    public FullTeam setBoards(FullBoard[] boards) {
        this.boards = boards;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullTeam fullTeam = (FullTeam) o;
        return Arrays.equals(members, fullTeam.members) && Arrays.equals(boards, fullTeam.boards);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(members);
        result = 31 * result + Arrays.hashCode(boards);
        return result;
    }
}
