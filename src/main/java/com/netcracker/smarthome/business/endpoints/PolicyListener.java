package com.netcracker.smarthome.business.endpoints;

import com.netcracker.smarthome.ApplicationContextHolder;
import com.netcracker.smarthome.model.entities.Policy;
import org.reflections.Reflections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PolicyListener {/*implements IListener, IListenerSupport<IListener> {
    List<IListener> listeners = new ArrayList<IListener>();

    public PolicyListener() {
        initListeners();
    }

    public void initListeners() {
        Reflections reflections = new Reflections("com.netcracker.smarthome");
        Set<Class<? extends IListener>> subTypes = reflections.getSubTypesOf(IListener.class);
        for (Class cl : subTypes) {
            String[] name = cl.getName().split("(?<=\\.)");
            if (!name[name.length-1].equals("PolicyListener"))
                listeners.add((IListener)ApplicationContextHolder.getApplicationContext().getBean(name[name.length-1]));
        }
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    public void onSaveOrUpdate(Long smartHouseId, Object object) {
        for(IListener listener : listeners) {
            listener.onSaveOrUpdate(((Policy)object).getCatalog().getSmartHome().getSmartHomeId(),object);
        }
    }*/
}
