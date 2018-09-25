package com.avalon.common.menu;

/**
 * 按钮控制
 * 命名规范 
 * 1.以op_开头
 */
public class Button {
    /**
     * 订单操作
     */
    public static final String ORDER_OPEARTION = "op_order_opeartion";

    private String             name;

    private String             permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
