package hu.vadavar.rija.models.users;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.teams.FullTeam;

public class FullUser extends User {
    private List<FullTeam> teams;

    public FullUser() {
        super();
    }

    public FullUser(String id, String username, String email, List<FullTeam> teams) {
        super(id, username, email, teams.stream().map(FullTeam::getId).collect(Collectors.toList()));
        this.teams = teams;
    }

    public FullUser(User user, List<FullTeam> teams) {
        super(user);
        this.teams = teams;
    }

    public List<FullTeam> getTeams() {
        return teams;
    }

    public FullUser setTeams(List<FullTeam> teams) {
        this.teams = teams;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullUser fullUser = (FullUser) o;
        return Objects.equals(teams, fullUser.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), teams);
    }
}