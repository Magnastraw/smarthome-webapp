package com.netcracker.smarthome.web.home;

import com.netcracker.smarthome.dal.repositories.DataTypeRepository;
import com.netcracker.smarthome.model.entities.DataType;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@FacesConverter("dataTypeConverter")
public class DataTypeConverter implements Converter {
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext) facesContext.getCurrentInstance().getExternalContext().getContext());
        if (value != null && value.trim().length() > 0) {
            try {
                DataTypeRepository repo = (DataTypeRepository) context.getBean("dataTypeRepository");
                return repo.get(Long.parseLong(value));
            } catch (NumberFormatException e) {
                ContextUtils.addErrorMessageToContext("Conversion error: not a valid data type!");
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null) {
            return String.valueOf(((DataType) object).getTypeId());
        } else {
            return null;
        }
    }
}
