package com.netcracker.smarthome.web.common;

import com.netcracker.smarthome.model.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ContextUtils {
    public static void addErrorMessageToContext(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
                message));
    }

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User))
            return null;
        return (User) principal;
    }
}
