package com.iuling.mods.clothingmanageformj.param;

import java.util.List;

/**
 * 绑定相关参数
 */
public class KindclothingBindParam {
    private String barCode; //条码
    private String epc;  //标签
    private List<String> epcList;

    public List<String> getEpcList() {
        return epcList;
    }

    public void setEpcList(List<String> epcList) {
        this.epcList = epcList;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
