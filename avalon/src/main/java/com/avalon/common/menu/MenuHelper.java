package com.avalon.common.menu;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avalon.common.util.ClassUtil;

/**
 * 菜单工具类
 * 菜单命名规范
 * 1.主菜单已   main_ 开头
 * 2.子菜单已 sub_开头
 * 3.所有标识符都建常量
 * 
 * @author jiangping
 *
 */
public class MenuHelper {

    /*******************订单********************/
    public final static String               MAIN_ORDER       = "main_order";
    public final static String               SUB_ORDER_LIST   = "sub_order_list";
    public final static String               SUB_REFUND_LIST  = "sub_refund_list";

    /*******************财务********************/
    public final static String               MAIN_FINANCE     = "main_finance";
    public final static String               SUB_FINANCE_LIST = "sub_finance_list";

    private static List<Menu>                menus            = new ArrayList<Menu>();

    private static Map<String, List<Button>> buttonMap        = new HashMap<String, List<Button>>();

    static {
        menus.add(orderMenu(1));
        menus.add(financeMenu(2));

        setButtonMap();
        for (Menu menu : menus) {
            if (menu.getMenus() != null) {
                for (Menu sub : menu.getMenus()) {
                    sub.setButtons(buttonMap.get(sub.getPermission()));
                }
            }
        }
        Collections.sort(menus);
    }

    private static Menu orderMenu(int sort) {
        List<Menu> menus = new ArrayList<Menu>();
        menus.add(new Menu("订单管理", SUB_ORDER_LIST, "/order/list.do", 1));
        menus.add(new Menu("退款管理", SUB_REFUND_LIST, "/refund/list.do", 0));
        Collections.sort(menus);
        return new Menu("订单", MAIN_ORDER, menus, sort);

    }

    private static Menu financeMenu(int sort) {
        List<Menu> menus = new ArrayList<Menu>();
        menus.add(new Menu("财务管理", SUB_FINANCE_LIST, "/finance/list.do", 1));
        Collections.sort(menus);
        return new Menu("财务", MAIN_FINANCE, menus, sort);

    }

    public static List<Menu> getMainMenus() {
        return menus;
    }

    public static Menu getMainMenuBySubmenu(String subPermission) {
        for (Menu menu : menus) {
            if (menu.getMenus() != null) {
                for (Menu submenu : menu.getMenus()) {
                    if (submenu.getPermission().equals(subPermission)) {
                        return menu;
                    }
                }
            }
        }
        return menus.get(0);
    }

    public static List<Menu> getSubmenu(String mainPermission) {
        for (Menu menu : menus) {
            if (menu.getPermission().equals(mainPermission)) {
                return menu.getMenus();
            }
        }
        return new ArrayList<Menu>();
    }

    private static void setButtonMap() {
        List<Class<?>> classes = ClassUtil.getClasses("com.blderp.admin.controller");
        for (Class<?> cls : classes) {
            Method[] methods = cls.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    AuthButton authButton = method.getAnnotation(AuthButton.class);
                    if (authButton != null) {
                        String menu = authButton.menu();
                        List<Button> buttons = buttonMap.get(menu);
                        if (buttons == null) {
                            buttons = new ArrayList<Button>();
                        }
                        boolean exist = false;
                        for (Button button : buttons) {
                            if (button.getPermission().equals(authButton.permission())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            Button button = new Button();
                            button.setName(authButton.name());
                            button.setPermission(authButton.permission());
                            buttons.add(button);
                        }
                        buttonMap.put(menu, buttons);
                    }
                }
            }
        }
    }
}
