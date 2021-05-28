package com.example.agilesavev2.models.users;

public class UserPayInfo {
    int id, userid;
    String nextpaydate;
    String paydaytype;
    Double pay;

    public UserPayInfo(int id, int userID, String nextPayDate, String payDayType, Double pay) {
        this.id = id;
        this.userid = userID;
        this.nextpaydate = nextPayDate;
        this.paydaytype = payDayType;
        this.pay = pay;
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

    public String getNextPayDate() {
        return nextpaydate;
    }

    public void setNextPayDate(String nextPayDate) {
        this.nextpaydate = nextPayDate;
    }

    public String getPayDayType() {
        return paydaytype;
    }

    public void setPayDayType(String payDayType) {
        this.paydaytype = payDayType;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "UserPayInfo{" +
                "id=" + id +
                ", userID=" + userid +
                ", nextPayDate=" + nextpaydate +
                ", payDayType='" + paydaytype + '\'' +
                ", pay=" + pay +
                '}';
    }
}
