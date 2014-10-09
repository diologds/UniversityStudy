package lv.rtu.domain;

import java.util.Date;

public class LoginInformation {
    private String token;
    private Date date;

    public LoginInformation(String token){
        this.token = token;
        this.date = new Date();
    }

    public void updataTime(){
        this.date = new Date();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
