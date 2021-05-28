package com.example.agilesavev2.models.subscriptions;

public class Subscription {
    private int id, userid;
    private String name, category, started;
    private double subscriptionvalue;

    //constructor
    public Subscription(int id, int userID, String name, String category, String started, double subscriptionValue) {
        this.id = id;
        this.userid = userID;
        this.name = name;
        this.category = category;
        this.started = started;
        this.subscriptionvalue = subscriptionValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userid;
    }

    public void setUserID(int userID) {
        this.userid = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public double getSubscriptionValue() {
        return subscriptionvalue;
    }

    public void setSubscriptionValue(double subscriptionValue) {
        this.subscriptionvalue = subscriptionValue;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", userID=" + userid +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", started='" + started + '\'' +
                ", subscriptionValue=" + subscriptionvalue +
                '}';
    }
}
