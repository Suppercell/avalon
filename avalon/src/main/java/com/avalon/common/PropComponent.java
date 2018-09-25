package com.avalon.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.avalon.common.spring.SpringContext;

@Component
public class PropComponent {

    @Value(value = "${avalon.static.url}")
    private String staticUrl;

    public static PropComponent getProp() {
        return SpringContext.getBean(PropComponent.class);
    }

    public String getStaticUrl() {
        return staticUrl;
    }

}
