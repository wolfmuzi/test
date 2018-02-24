package com.iuling.mods.fabric.param;

import java.util.List;

/**
 * 上架相关参数
 */
public class FabricShelvesParam {
    private List<String> epcList;
    private Integer bindStatus;//绑定状态
    private Integer shelvesStatus;//上架状态
    private String shelvesId;//货架id

    public List<String> getEpcList() {
        return epcList;
    }

    public void setEpcList(List<String> epcList) {
        this.epcList = epcList;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public Integer getShelvesStatus() {
        return shelvesStatus;
    }

    public void setShelvesStatus(Integer shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    public String getShelvesId() {
        return shelvesId;
    }

    public void setShelvesId(String shelvesId) {
        this.shelvesId = shelvesId;
    }
}
