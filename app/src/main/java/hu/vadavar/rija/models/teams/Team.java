package hu.vadavar.rija.models.teams;

import java.util.Arrays;
import java.util.Objects;

public class Team {
    private String id;
    private String name;
    private String[] memberIds;
    private String[] boardIds;

    public Team() {}

    public Team(String id, String name, String[] memberIds, String[] boardIds) {
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

    public String[] getMemberIds() {
        return memberIds;
    }

    public Team setMemberIds(String[] memberIds) {
        this.memberIds = memberIds;
        return this;
    }

    public String[] getBoardIds() {
        return boardIds;
    }

    public Team setBoardIds(String[] boardIds) {
        this.boardIds = boardIds;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Arrays.equals(memberIds, team.memberIds) && Arrays.equals(boardIds, team.boardIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(memberIds);
        result = 31 * result + Arrays.hashCode(boardIds);
        return result;
    }
}
