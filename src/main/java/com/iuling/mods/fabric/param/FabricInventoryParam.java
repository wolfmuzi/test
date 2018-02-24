package com.iuling.mods.fabric.param;

import java.util.List;

/**
 * 绑定相关参数
 */
public class FabricInventoryParam {
    private String id;
    private String name ;
    private List<String> epcList;
    private List<OrderMapping> mappings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<OrderMapping> mappings) {
        this.mappings = mappings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getEpcList() {
        return epcList;
    }

    public void setEpcList(List<String> epcList) {
        this.epcList = epcList;
    }
}
