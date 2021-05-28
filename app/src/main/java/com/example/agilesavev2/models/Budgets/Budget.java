package com.example.agilesavev2.models.Budgets;

public class Budget {
    private int id, userid;
    private double target, progress;
    private String name, date, category;
    private boolean passed;

    public Budget(int id, int userid, double target, String name, boolean passed, String date,double progress, String category) {
        this.id = id;
        this.userid = userid;
        this.target = target;
        this.progress = progress;
        this.name = name;
        this.date = date;
        this.passed = passed;
        this.category = category;
    }

    public double getProgress() {
        return progress;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public double getTarget() {
        return target;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", userid=" + userid +
                ", target=" + target +
                ", progress=" + progress +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", passed=" + passed +
                '}';
    }
}
