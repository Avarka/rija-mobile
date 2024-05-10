package hu.vadavar.rija.models.teams;

import java.util.List;
import java.util.Objects;

public class Team {
    private String id;
    private String name;
    private List<String> members;
    private List<String> boards;

    public Team() {}

    public Team(String id, String name, List<String> members, List<String> boards) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.boards = boards;
    }

    public Team(Team team) {
        this.id = team.id;
        this.name = team.name;
        this.members = team.members;
        this.boards = team.boards;
    }

    public String getId() {
        return id;
    }

    public Team setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getMembers() {
        return members;
    }

    public Team setMembers(List<String> members) {
        this.members = members;
        return this;
    }

    public List<String> getBoards() {
        return boards;
    }

    public Team setBoards(List<String> boards) {
        this.boards = boards;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Objects.equals(members, team.members) && Objects.equals(boards, team.boards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members, boards);
    }
}