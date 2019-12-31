package com.bluetron.core.bean.epc;

import com.seuic.uhf.EPC;

/**
 * 包含epc数据和用户数据（“周围的rfid设备” activity中 adapter用到）
 */
public class EpcData {
    private EPC epc;
    private String userData;
    private String sbmc;

    public EpcData(EPC epc1, String userData1){
        this.epc = epc1;
        this.userData = userData1;
    }

    public EpcData(EPC epc1, String userData1,String sbmc){
        this.epc = epc1;
        this.userData = userData1;
        this.sbmc = sbmc;
    }

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc;
    }

    public EPC getEpc() {
        return epc;
    }

    public void setEpc(EPC epc) {
        this.epc = epc;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
}
