package org.moneydrop.app.DataClasses;

public class TaskTwoDataset {
    String name;
    String state;
    String link;

    public TaskTwoDataset(String name, String state, String link) {
        this.name = name;
        this.state = state;
        this.link = link;
    }

    public TaskTwoDataset() {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
