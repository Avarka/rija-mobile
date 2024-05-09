package hu.vadavar.rija.models.users;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public class User {
    private String id;
    private String username; //displayname
    private String email;
    private List<String> teamIds;

    public User() {}

    public User(String id, String username, String email, List<String> teamIds) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.teamIds = teamIds;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.teamIds = user.teamIds;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<String> getTeamIds() {
        return teamIds;
    }

    public User setTeamIds(List<String> teamIds) {
        this.teamIds = teamIds;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(teamIds, user.teamIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, teamIds);
    }
}
