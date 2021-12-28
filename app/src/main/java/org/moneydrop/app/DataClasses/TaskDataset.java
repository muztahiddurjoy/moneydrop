package org.moneydrop.app.DataClasses;

public class TaskDataset {
    String name;
    String state;

    public TaskDataset() {
    }

    public TaskDataset(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
