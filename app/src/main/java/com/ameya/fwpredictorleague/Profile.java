package com.ameya.fwpredictorleague;

/**
 * Created by ameya on 8/20/2017.
 */

public class Profile {
    String id;
    String name;
    String first_name;
    String last_name;
    String username;
    String emailId;
    String pwd;
    String team_id;
    String team_name;
    Long team_rank;
    String opp_team_id;
    String opp_team_name;
    Long opp_team_rank;


    static Profile p = null;

    public Profile() {
    }

    public static Profile getInstance() {
        if (p == null) {
            synchronized (Profile.class) {
                if (p == null) {
                    p = new Profile();
                }
            }
        }
        return p;
    }

    public static void resetInstance() {
        if (p != null) {
            p = getInstance();
        }
        p = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Long getTeam_rank() {
        return team_rank;
    }

    public void setTeam_rank(Long team_rank) {
        this.team_rank = team_rank;
    }

    public String getOpp_team_id() {
        return opp_team_id;
    }

    public void setOpp_team_id(String opp_team_id) {
        this.opp_team_id = opp_team_id;
    }

    public String getOpp_team_name() {
        return opp_team_name;
    }

    public void setOpp_team_name(String opp_team_name) {
        this.opp_team_name = opp_team_name;
    }

    public Long getOpp_team_rank() {
        return opp_team_rank;
    }

    public void setOpp_team_rank(Long opp_team_rank) {
        this.opp_team_rank = opp_team_rank;
    }
}