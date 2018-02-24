package com.iuling.mods.clothingmanageformj.param;

import java.util.List;

/**
 * 样衣借用相关参数
 */
public class KindclothingLendParam {
    private List<String> epcList;
    private Integer isLend;//是否借出
    private Integer shelvesStatus;//上架状态
    private String name; //借用人姓名
    private Integer type; //类型
    private String lendUserId; //借用人id
    private Integer lendIsReturn; //是否归还状态
    private Integer returnNum;  //本次归还数量
    private String barCode1;   //条形码

    public List<String> getEpcList() {
        return epcList;
    }

    public void setEpcList(List<String> epcList) {
        this.epcList = epcList;
    }

    public Integer getIsLend() {
        return isLend;
    }

    public void setIsLend(Integer isLend) {
        this.isLend = isLend;
    }

    public Integer getShelvesStatus() {
        return shelvesStatus;
    }

    public void setShelvesStatus(Integer shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLendUserId() {
        return lendUserId;
    }

    public void setLendUserId(String lendUserId) {
        this.lendUserId = lendUserId;
    }

    public Integer getLendIsReturn() {
        return lendIsReturn;
    }

    public void setLendIsReturn(Integer lendIsReturn) {
        this.lendIsReturn = lendIsReturn;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public String getBarCode1() {
        return barCode1;
    }

    public void setBarCode1(String barCode1) {
        this.barCode1 = barCode1;
    }
}
