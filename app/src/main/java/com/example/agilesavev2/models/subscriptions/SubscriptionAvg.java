package com.example.agilesavev2.models.subscriptions;

public class SubscriptionAvg {
    private String id, category, avg;

    public SubscriptionAvg(String id, String category, String avg) {
        this.id = id;
        this.category = category;
        this.avg = avg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "SubscriptionAvg{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", avg='" + avg + '\'' +
                '}';
    }
}
