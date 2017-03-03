package com.netcracker.smarthome.web.chart.ChartConverters;

import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@FacesConverter("smartObjectConverter")
public class SmartObjectConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext) facesContext.getCurrentInstance().getExternalContext().getContext());
        if (value != null && value.trim().length() > 0) {
            try {
                SmartObjectRepository repo = (SmartObjectRepository) context.getBean("smartObjectRepository");
                return repo.get(Long.parseLong(value));
            } catch (NumberFormatException e) {
                ContextUtils.addErrorMessageToContext("Conversion error: not a valid data type!");
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null) {
            System.out.println(String.valueOf(((SmartObject) object).getSmartObjectId()));
            return String.valueOf(((SmartObject) object).getSmartObjectId());
        } else {
            return null;
        }
    }
}