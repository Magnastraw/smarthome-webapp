package com.netcracker.smarthome.business.endpoints;

import com.netcracker.smarthome.business.endpoints.controllers.PoliciesController;
import com.netcracker.smarthome.business.endpoints.jsonentities.HomeTask;
import com.netcracker.smarthome.model.entities.Action;
import com.netcracker.smarthome.model.entities.Condition;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskManager implements IListener{
    private static final Logger LOG = LoggerFactory.getLogger(PoliciesController.class);
    private Map<Long, List<HomeTask>> taskMap;

    @PostConstruct
    private void init() {
        taskMap = new HashMap<Long, List<HomeTask>>();
    }

    public void addHomeTask(Long smartHouseId, HomeTask homeTask) {
        List<HomeTask> list = taskMap.get(smartHouseId);
        if (list != null) {
            boolean exists = false;
            for (HomeTask task : list) {
                if (task.getAction().equals(homeTask.getAction())) {
                    exists = true;
                    break;
                }
            }
            if (!exists)
                taskMap.get(smartHouseId).add(homeTask);
        }
        else {
            taskMap.put(smartHouseId, new ArrayList<HomeTask>());
            taskMap.get(smartHouseId).add(homeTask);
        }
    }

    public void onSaveOrUpdate(Object object) {
        Long smartHomeId = null;
        if (object instanceof Policy)
            smartHomeId = ((Policy)object).getCatalog().getSmartHome().getSmartHomeId();
        else if (object instanceof Rule)
            smartHomeId = ((Rule)object).getPolicy().getCatalog().getSmartHome().getSmartHomeId();
        else if (object instanceof Action)
            smartHomeId = ((Action)object).getRule().getPolicy().getCatalog().getSmartHome().getSmartHomeId();
        else if (object instanceof Condition)
            smartHomeId = ((Condition)object).getRule().getPolicy().getCatalog().getSmartHome().getSmartHomeId();
        if (smartHomeId != null)
            addHomeTask(smartHomeId, new HomeTask("GetPolicy"));
        else
            LOG.warn("SmartHome not found");
    }

    public Map<Long, List<HomeTask>> getTaskMap() {
        return taskMap;
    }

    public void setTaskMap(Map<Long, List<HomeTask>> taskMap) {
        this.taskMap = taskMap;
    }

}