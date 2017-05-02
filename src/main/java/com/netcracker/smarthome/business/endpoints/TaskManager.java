package com.netcracker.smarthome.business.endpoints;

import com.netcracker.smarthome.business.endpoints.jsonentities.HomeTask;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskManager implements IListener{
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

    public void onSaveOrUpdate(Long smartHouseId, Object object) {
        addHomeTask(smartHouseId, new HomeTask("GetPolicy"));
    }

    public Map<Long, List<HomeTask>> getTaskMap() {
        return taskMap;
    }

    public void setTaskMap(Map<Long, List<HomeTask>> taskMap) {
        this.taskMap = taskMap;
    }

}