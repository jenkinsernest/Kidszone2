package com.playzone.kidszone.models;


public class Goals_Model {


    private String kid;
    private String p_email;
    private String taskname;
    private String alotted_time;
    private String Active_day;
    private String status = "not active";
    private String spent;
    private String learn_first = "false";
    private String reward_earned = "0";


    public Goals_Model() {


        this.p_email = p_email;
        this.kid = kid;
        this.taskname = taskname;
        this.alotted_time = alotted_time;

        this.Active_day = Active_day;
        this.status = status;
        this.spent = spent;
        this.learn_first = learn_first;
        this.reward_earned = reward_earned;


    }
    public Goals_Model(String p_email, String kid, String taskname, String alotted_time, String active_day, String status, String spent, String Learn) {


        this.p_email = p_email;
        this.kid = kid;
        this.taskname = taskname;
        this.alotted_time = alotted_time;

        this.Active_day = active_day;
        this.status = status;
        this.spent = spent;
        this.learn_first = Learn;
        this.reward_earned = reward_earned;


    }


    public void setP_email(String pemail) {
        this.p_email = pemail;

    }

    public String getP_email() {
        return p_email;
    }

   public void setReward_earned(String reward) {
        this.reward_earned = reward;

    }

    public String getReward_earned() {
        return reward_earned;
    }

public void setLearn_first(String learn) {
        this.learn_first = learn;

    }

    public String getLearn_first() {
        return learn_first;
    }

public void setSpent(String spent) {
        this.spent = spent;

    }

    public String getSpent() {
        return spent;
    }


    public void setKid(String kid) {
        this.kid = kid;

    }

    public String getKid() {
        return kid;
    }


    public void setTaskname(String task) {
        this.taskname = task;

    }

    public String getTaskname() {
        return taskname;
    }


    public void setAlotted_time(String alotted_time) {
        this.alotted_time = alotted_time;

    }

    public String getAlotted_time() {
        return alotted_time;
    }


    public String getActive_day() {
        return Active_day;
    }

    public void setActive_day(String day) {
        this.Active_day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
