package com.bluetron.rxretrohttp.bean.login;


/**
 * @author tongxb
 * @date 2018/3/29
 */
public class LogOutEvent {
    private boolean isManual;
    private String logoutMsg;

    public LogOutEvent(boolean isManual) {
        this.isManual = isManual;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }

    public String getLogoutMsg() {
        return logoutMsg;
    }

    public void setLogoutMsg(String logoutMsg) {
        this.logoutMsg = logoutMsg;
    }
}
