package com.netcracker.smarthome.web.chart.ChartConverters;


import com.netcracker.smarthome.dal.repositories.MetricSpecRepository;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@FacesConverter("metricSpecConverter")
public class MetricSpecConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext((ServletContext) facesContext.getCurrentInstance().getExternalContext().getContext());
        if (value != null && value.trim().length() > 0) {
            try {
                MetricSpecRepository repo = (MetricSpecRepository) context.getBean("metricSpecRepository");
                return repo.get(Long.parseLong(value));
            } catch (NumberFormatException e) {
                ContextUtils.addErrorMessageToContext("Conversion error: not a valid data type!");
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null) {
            return String.valueOf(((MetricSpec) object).getSpecId());
        } else {
            return null;
        }
    }
}
