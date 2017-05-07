package com.netcracker.smarthome.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean {
    private String pageName = "/faces/home/settings";
    private String oldPage;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.oldPage = this.pageName;
        this.pageName = "/faces/" + pageName;
    }

    public String getOldPage() {
        return this.oldPage;
    }

    public void setOldPage(String oldPage) {
        this.oldPage = oldPage;
    }

    public void toPrevPage(){
        this.pageName = this.oldPage;
    }

}