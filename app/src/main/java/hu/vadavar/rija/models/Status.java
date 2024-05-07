package hu.vadavar.rija.models;

public class Status {
    private String id;
    private String name;
    private String color;
    private String[] previousStatusIds;
    private String[] nextStatusIds;

    public Status() {}

    public Status(String id, String name, String color, String[] previousStatusIds, String[] nextStatusIds) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.previousStatusIds = previousStatusIds;
        this.nextStatusIds = nextStatusIds;
    }

    public Status(Status status) {
        this.id = status.id;
        this.name = status.name;
        this.color = status.color;
        this.previousStatusIds = status.previousStatusIds;
        this.nextStatusIds = status.nextStatusIds;
    }

    public String getId() {
        return id;
    }

    public Status setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Status setName(String name) {
        this.name = name;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Status setColor(String color) {
        this.color = color;
        return this;
    }

    public String[] getPreviousStatusIds() {
        return previousStatusIds;
    }

    public Status setPreviousStatusIds(String[] previousStatusIds) {
        this.previousStatusIds = previousStatusIds;
        return this;
    }

    public String[] getNextStatusIds() {
        return nextStatusIds;
    }

    public Status setNextStatusIds(String[] nextStatusIds) {
        this.nextStatusIds = nextStatusIds;
        return this;
    }
}
