package hu.vadavar.rija.models.users;

import java.util.Arrays;

import hu.vadavar.rija.models.teams.FullTeam;

public class FullUser extends User {
    private FullTeam[] teams;

    public FullUser() {
        super();
    }

    public FullUser(String id, String username, String email, FullTeam[] teams) {
        super(id, username, email, Arrays.stream(teams).map(FullTeam::getId).toArray(String[]::new));
        this.teams = teams;
    }

    public FullUser(User user, FullTeam[] teams) {
        super(user);
        this.teams = teams;
    }

    public FullTeam[] getTeams() {
        return teams;
    }

    public FullUser setTeams(FullTeam[] teams) {
        this.teams = teams;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FullUser fullUser = (FullUser) o;
        return Arrays.equals(teams, fullUser.teams);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(teams);
        return result;
    }
}
