package com.iuling.mods.base.param;

import com.iuling.core.persistence.DataEntity;

/**
 * 表单显示
 */
public class CommonRoleVo extends DataEntity<CommonRoleVo> {
    private String yue;
    private String name;
    private String getNum;
    private String orderNum;

    public String getYue() {
        return yue;
    }

    public void setYue(String yue) {
        this.yue = yue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGetNum() {
        return getNum;
    }

    public void setGetNum(String getNum) {
        this.getNum = getNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
