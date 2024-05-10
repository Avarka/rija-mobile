package hu.vadavar.rija.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Status implements Parcelable {
    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @NonNull
        @Override
        public Status createFromParcel(@NonNull Parcel in) {
            new Status();
            return new Status(in);
        }

        @NonNull
        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
    private String id;
    private String name;
    private String color;
    private List<String> previousStatusIds;
    private List<String> nextStatusIds;

    public Status() {}

    public Status(String id, String name, String color, List<String> previousStatusIds, List<String> nextStatusIds) {
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

    public Status(Parcel in) {
        id = in.readString();
        name = in.readString();
        color = in.readString();
        previousStatusIds = in.createStringArrayList();
        nextStatusIds = in.createStringArrayList();
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

    public List<String> getPreviousStatusIds() {
        return previousStatusIds;
    }

    public Status setPreviousStatusIds(List<String> previousStatusIds) {
        this.previousStatusIds = previousStatusIds;
        return this;
    }

    public List<String> getNextStatusIds() {
        return nextStatusIds;
    }

    public Status setNextStatusIds(List<String> nextStatusIds) {
        this.nextStatusIds = nextStatusIds;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.color);
        dest.writeStringList(this.previousStatusIds);
        dest.writeStringList(this.nextStatusIds);
    }
}
