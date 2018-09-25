package com.avalon.common.menu;

import java.util.List;

public class Menu implements Comparable<Menu> {
    private String       name;

    private String       permission;

    private String       url;

    private List<Menu>   menus;

    private List<Button> buttons;

    private int          sort;

    public Menu(String name, String permission, List<Menu> menus, int sort) {
        super();
        this.name = name;
        this.permission = permission;
        this.menus = menus;
        this.sort = sort;
    }

    public Menu(String name, String permission, String url, int sort) {
        super();
        this.name = name;
        this.permission = permission;
        this.url = url;
        this.sort = sort;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    @Override
    public int compareTo(Menu o) {
        return this.sort - o.sort;
    }

}
