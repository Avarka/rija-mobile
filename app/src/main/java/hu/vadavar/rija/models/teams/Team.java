package hu.vadavar.rija.models.teams;

import java.util.List;
import java.util.Objects;

public class Team {
    private String id;
    private String name;
    private List<String> memberIds;
    private List<String> boardIds;

    public Team() {}

    public Team(String id, String name, List<String> memberIds, List<String> boardIds) {
        this.id = id;
        this.name = name;
        this.memberIds = memberIds;
        this.boardIds = boardIds;
    }

    public Team(Team team) {
        this.id = team.id;
        this.name = team.name;
        this.memberIds = team.memberIds;
        this.boardIds = team.boardIds;
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

    public List<String> getMemberIds() {
        return memberIds;
    }

    public Team setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
        return this;
    }

    public List<String> getBoardIds() {
        return boardIds;
    }

    public Team setBoardIds(List<String> boardIds) {
        this.boardIds = boardIds;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Objects.equals(memberIds, team.memberIds) && Objects.equals(boardIds, team.boardIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, memberIds, boardIds);
    }
}