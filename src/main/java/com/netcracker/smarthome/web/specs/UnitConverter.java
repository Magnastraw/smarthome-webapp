package com.netcracker.smarthome.web.specs;

import com.netcracker.smarthome.dal.repositories.UnitRepository;
import com.netcracker.smarthome.model.entities.Unit;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@FacesConverter("unitConverter")
public class UnitConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext) facesContext.getCurrentInstance().getExternalContext().getContext());
        if(value != null && value.trim().length() > 0) {
            try {
                UnitRepository unitRepository = (UnitRepository) context.getBean("unitRepository");
                return unitRepository.get(Long.parseLong(value));
            } catch(NumberFormatException e) {
                ContextUtils.addErrorMessageToContext("Conversion error: not a valid unit");
            }
        }
        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Unit) object).getUnitId());
        }
        else {
            return null;
        }
    }
}