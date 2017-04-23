package com.netcracker.smarthome.web.inventory;

import com.netcracker.smarthome.business.alarm.AlarmService;
import com.netcracker.smarthome.business.endpoints.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.Alarm;
import com.netcracker.smarthome.model.entities.ObjectType;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="inventoryBean")
@SessionScoped
public class InventoryBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryBean.class);
    private OrganigramNode rootNode;
    private OrganigramNode selectedNode;
    private String style;
    private List<String> objectTypes;
    private List<Alarm> selectedObjectAlarms;

    @ManagedProperty(value = "#{smartObjectService}")
    private SmartObjectService smartObjectService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;
    @ManagedProperty(value = "#{alarmService}")
    private AlarmService alarmService;

    @PostConstruct
    public void init() {
        objectTypes = smartObjectService.getObjectTypes();
        for(int i=0; i<objectTypes.size(); i++) {
            objectTypes.set(i, objectTypes.get(i).toLowerCase());
        }
        SmartObject rootObject = smartObjectService.getRootController(getHome().getSmartHomeId());
        rootNode = new DefaultOrganigramNode("rootcontroller", rootObject, null);
        rootNode.setCollapsible(false);
        rootNode.setSelectable(true);
        addSubobjects(rootNode);

    }

    protected void addSubobjects(OrganigramNode parent) {
        List<SmartObject> subobjects = smartObjectService.getSubobjectsByObjectId(((SmartObject)parent.getData()).getSmartObjectId());
        if(subobjects.size() != 0) {
            for (SmartObject subobject : subobjects) {
                OrganigramNode objectNode = new DefaultOrganigramNode(subobject.getObjectType().getName().toLowerCase(), subobject, parent);
                objectNode.setDroppable(true);
                objectNode.setDraggable(true);
                objectNode.setSelectable(true);
                addSubobjects(objectNode);
            }
        }
        /*OrganigramNode divisionNode = new DefaultOrganigramNode("division", name, parent);
        divisionNode.setDroppable(true);
        divisionNode.setDraggable(true);
        divisionNode.setSelectable(true);

        if (employees != null) {
            for (String employee : employees) {
                OrganigramNode employeeNode = new DefaultOrganigramNode("employee", employee, divisionNode);
                employeeNode.setDraggable(true);
                employeeNode.setSelectable(true);
            }
        }
*/
    }

    public void onSelectNode(OrganigramNodeSelectEvent event) {
        SmartObject selectedObject = (SmartObject)event.getOrganigramNode().getData();
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + selectedObject.getName() + "' selected." +
                "\nParent: " + selectedObject.getParentObject().getName());
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onClickContextMenu(OrganigramNodeSelectEvent event) {
        SmartObject selectedObject = (SmartObject)selectedNode.getData();
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + selectedObject.getName() + "' selected.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
        selectedObjectAlarms = alarmService.getAlarmsByObject(selectedObject.getSmartObjectId());
    }

    public List<Alarm> selectedObjectAlarms() {
        if(selectedNode == null)
            return null;
        List<Alarm>  alarms = alarmService.getAlarmsByObject(((SmartObject)selectedNode.getData()).getSmartObjectId());
        return alarms;
    }

    private SmartHome getHome() {
        return userHomesBean.getCurrentHome();
    }

    public OrganigramNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }

    public void setSmartObjectService(SmartObjectService smartObjectService) {
        this.smartObjectService = smartObjectService;
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }

    public OrganigramNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(OrganigramNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<String> getObjectTypes() {
        return objectTypes;
    }

    public void setObjectTypes(List<String> objectTypes) {
        this.objectTypes = objectTypes;
    }

    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    public List<Alarm> getSelectedObjectAlarms() {
        return selectedObjectAlarms;
    }

    public void setSelectedObjectAlarms(List<Alarm> selectedObjectAlarms) {
        this.selectedObjectAlarms = selectedObjectAlarms;
    }
}
