package com.iuling.mods.base.param;

import java.util.ArrayList;
import java.util.List;

/**
 * 货架信息参数相关
 */
public class ShelvesParam {
    private String barCode;
    private String type;
    private String name;
    private String ShelvesId ;
    private String epc ;
    private List<String> epcList = new ArrayList<>();

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

    public String getShelvesId() {
        return ShelvesId;
    }

    public void setShelvesId(String shelvesId) {
        ShelvesId = shelvesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
