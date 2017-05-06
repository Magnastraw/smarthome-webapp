package com.netcracker.smarthome.business.endpoints;

import com.netcracker.smarthome.ApplicationContextHolder;
import org.reflections.Reflections;
import java.util.HashSet;
import java.util.Set;

public class ListenersInitializer {
    /*public static Set<IListener> init() {
        Set<IListener> listeners = new HashSet<IListener>();
        Reflections reflections = new Reflections("com.netcracker.smarthome");
        Set<Class<? extends IListener>> subTypes = reflections.getSubTypesOf(IListener.class);
        for (Class cl : subTypes) {
            String[] name = cl.getName().split("(?<=\\.)");
            String beanName = name[name.length-1].substring(0,1).toLowerCase()+name[name.length-1].substring(1);
            try {
                listeners.add((IListener) ApplicationContextHolder.getApplicationContext().getBean(beanName));
            } catch (Exception ex) {}
        }
        return listeners;
    }*/
}
