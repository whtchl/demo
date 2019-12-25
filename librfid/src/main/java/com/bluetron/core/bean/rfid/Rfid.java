package com.bluetron.core.bean.rfid;

public class Rfid {
    String uuid;
    String userData;
    public Rfid(){
    }
    public Rfid(String str1,String str2){
        this.uuid = str1;
        this.userData = str2;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
}
