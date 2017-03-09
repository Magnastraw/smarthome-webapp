package com.netcracker.smarthome.web.specs;

import com.netcracker.smarthome.dal.repositories.CatalogRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@FacesConverter("catalogConverter")
public class CatalogConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext) facesContext.getCurrentInstance().getExternalContext().getContext());
        if(value != null && value.trim().length() > 0) {
            try {
                CatalogRepository catalogRepository = (CatalogRepository) context.getBean("catalogRepository");
                return catalogRepository.get(Long.parseLong(value));
            } catch(NumberFormatException e) {
                ContextUtils.addErrorMessageToContext("Conversion error: not a valid catalog");
            }
        }
        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Catalog) object).getCatalogId());
        }
        else {
            return null;
        }
    }
}