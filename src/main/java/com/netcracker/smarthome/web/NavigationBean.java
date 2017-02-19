package com.netcracker.smarthome.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean {
    private String pageName = "/faces/home/settings";

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = "/faces/" + pageName;
    }
}