package hu.vadavar.rija.models.teams;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.boards.FullBoard;
import hu.vadavar.rija.models.users.User;

public class FullTeam extends Team {
    private List<User> fMembers;
    private List<FullBoard> fBoards;

    public FullTeam() {
        super();
    }

    public FullTeam(String id, String name, List<User> fMembers, List<FullBoard> fBoards) {
        super(id, name, fMembers.stream().map(User::getId).collect(Collectors.toList()), fBoards.stream().map(FullBoard::getId).collect(Collectors.toList()));
        this.fMembers = fMembers;
        this.fBoards = fBoards;
    }

    public FullTeam(Team team, List<User> fMembers, List<FullBoard> fBoards) {
        super(team);
        this.fMembers = fMembers;
        this.fBoards = fBoards;
    }

    public List<User> getfMembers() {
        return fMembers;
    }

    public FullTeam setfMembers(List<User> fMembers) {
        this.fMembers = fMembers;
        return this;
    }

    public List<FullBoard> getfBoards() {
        return fBoards;
    }

    public FullTeam setfBoards(List<FullBoard> fBoards) {
        this.fBoards = fBoards;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullTeam fullTeam = (FullTeam) o;
        return Objects.equals(fMembers, fullTeam.fMembers) && Objects.equals(fBoards, fullTeam.fBoards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fMembers, fBoards);
    }
}