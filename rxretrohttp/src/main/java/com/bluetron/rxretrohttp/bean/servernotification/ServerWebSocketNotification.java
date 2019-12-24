package com.bluetron.rxretrohttp.bean.servernotification;

public class ServerWebSocketNotification {

    private Extra extra;

    private int sysPushType;

    private String text;

    private String title;

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public int getSysPushType() {
        return sysPushType;
    }

    public void setSysPushType(int sysPushType) {
        this.sysPushType = sysPushType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public class Extra{

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
