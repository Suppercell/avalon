package com.avalon.tools;

import com.avalon.common.PropComponent;

public class ResourceTools {

    public static String getStaticUrl() {
        return PropComponent.getProp().getStaticUrl();
    }
}
