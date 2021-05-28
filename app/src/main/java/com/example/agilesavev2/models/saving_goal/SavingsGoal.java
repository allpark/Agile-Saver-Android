package com.example.agilesavev2.models.saving_goal;

public class SavingsGoal {
    private int id, userid;
    private String goalname, targetdate, achieveddate, startDate;
    private double targetgoal, progress;
    private boolean reached;


        /*
            id SERIAL PRIMARY KEY,
            userID INTEGER,
            goalName TEXT,
            targetDate DATE,
            startDate DATE,
            reached BOOLEAN ,
            progress FLOAT,
            targetGoal FLOAT,
            achievedDate DATE
     */

    //map it here that fine

    //required: int,int,String,String,String,boolean,double,double,String
    //  found: int,int,String,String,String,String,double,boolean

    // constructor
    public SavingsGoal(int id, int userID, String goalname, String targetdate, String startDate, boolean reached, double progress, double targetgoal, String achieveddate) {
        this.id = id;
        this.userid = userID;
        this.goalname = goalname;
        this.targetdate = targetdate;
        this.achieveddate = achieveddate;
        this.targetgoal = targetgoal;
        this.startDate = startDate;
        this.progress = progress;
        this.reached = reached;
    }

    // getters
    public int getId() {
        return id;
    }
    public int getUserID() {
        return userid;
    }
    public String getGoalname() {
        return goalname;
    }
    public String getTargetdate() {
        return targetdate;
    }
    public String getAchieveddate() {
        return achieveddate;
    }
    public String getStartDate() {
        return startDate;
    }
    public double getProgress() {
        return progress;
    }
    public double getTargetGoal() { return targetgoal; }

    public boolean isReached() {
        return reached;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }
    public void setUserID(int userID) {
        this.userid = userID;
    }
    public void setGoalname(String goalname) {
        this.goalname = goalname;
    }
    public void setTargetdate(String targetdate) {
        this.targetdate = targetdate;
    }
    public void setAchieveddate(String achieveddate) {
        this.achieveddate = achieveddate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setProgress(double progress) {
        this.progress = progress;
    }
    public void setReached(boolean reached) {
        this.reached = reached;
    }
    public void setTargetGoal(double goal) { this.targetgoal = goal; }

    @Override
    public String toString() {
        return "SavingsGoal{" +
                "id=" + id +
                ", userID=" + userid +
                ", goalname='" + goalname + '\'' +
                ", targetdate='" + targetdate + '\'' +
                ", achieveddate='" + achieveddate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", progress=" + progress +
                ", targetGoal=" + targetgoal +
                ", reached=" + reached +
                '}';
    }
}


