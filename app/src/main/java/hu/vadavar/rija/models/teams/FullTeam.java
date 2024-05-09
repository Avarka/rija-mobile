package hu.vadavar.rija.models.teams;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.boards.FullBoard;
import hu.vadavar.rija.models.users.User;

public class FullTeam extends Team {
    private List<User> members;
    private List<FullBoard> boards;

    public FullTeam() {
        super();
    }

    public FullTeam(String id, String name, List<User> members, List<FullBoard> boards) {
        super(id, name, members.stream().map(User::getId).collect(Collectors.toList()), boards.stream().map(FullBoard::getId).collect(Collectors.toList()));
        this.members = members;
        this.boards = boards;
    }

    public FullTeam(Team team, List<User> members, List<FullBoard> boards) {
        super(team);
        this.members = members;
        this.boards = boards;
    }

    public List<User> getMembers() {
        return members;
    }

    public FullTeam setMembers(List<User> members) {
        this.members = members;
        return this;
    }

    public List<FullBoard> getBoards() {
        return boards;
    }

    public FullTeam setBoards(List<FullBoard> boards) {
        this.boards = boards;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullTeam fullTeam = (FullTeam) o;
        return Objects.equals(members, fullTeam.members) && Objects.equals(boards, fullTeam.boards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members, boards);
    }
}