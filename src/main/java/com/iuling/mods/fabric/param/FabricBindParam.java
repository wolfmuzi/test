package com.iuling.mods.fabric.param;

import java.util.List;

/**
 * 绑定相关参数
 */
public class FabricBindParam {
    private String barCode;
    private String epc;
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
