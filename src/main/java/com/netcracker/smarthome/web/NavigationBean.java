package com.netcracker.smarthome.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//
//@ManagedBean(name = "navigationBean")
//@SessionScoped
public class NavigationBean {

    private String pageName = "/faces/graph";

    public NavigationBean() {
    }

//    public void doNav() {
//        System.out.println("Hello");
//        String str = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("test");
//        this.pageName = str;
//    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}