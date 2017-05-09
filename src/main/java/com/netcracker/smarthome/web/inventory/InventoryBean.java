package com.netcracker.smarthome.web.inventory;

import com.netcracker.smarthome.business.services.AlarmService;
import com.netcracker.smarthome.business.endpoints.TaskManager;
import com.netcracker.smarthome.business.endpoints.jsonentities.HomeTask;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.business.services.CatalogService;
import com.netcracker.smarthome.model.entities.*;
import com.netcracker.smarthome.model.enums.AlarmSeverity;
import com.netcracker.smarthome.web.common.ContextUtils;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ManagedBean(name="inventoryBean")
@ViewScoped
public class InventoryBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryBean.class);
    private OrganigramNode rootNode;
    private OrganigramNode selectedNode;
    private SmartObject selectedObject;
    private String style;
    private List<String> objectTypes;
    private List<Alarm> selectedObjectAlarms;
    private Catalog rootCatalog;
    private List<Catalog> objectsCatalogs;
    private Catalog addedCatalog;
    private boolean adding;
    String selectedMaxSeverity;

    @ManagedProperty(value = "#{smartObjectService}")
    private SmartObjectService smartObjectService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;
    @ManagedProperty(value = "#{alarmService}")
    private AlarmService alarmService;
    @ManagedProperty(value = "#{catalogService}")
    private CatalogService catalogService;
    @ManagedProperty(value = "#{taskManager}")
    private TaskManager taskManager;


    @PostConstruct
    public void init() {
        setObjectTypes(smartObjectService.getObjectTypes());
        for(int i=0; i<objectTypes.size(); i++) {
            objectTypes.set(i, objectTypes.get(i).toLowerCase());
        }
        changeCurrentHome();
    }

    public void changeCurrentHome() {
        reset();
        SmartObject rootObject = smartObjectService.getRootController(getHome().getSmartHomeId());
        if(rootObject != null) {
            rootNode = new DefaultOrganigramNode("rootcontroller", rootObject, null);
            rootNode.setCollapsible(false);
            rootNode.setSelectable(true);
            addSubobjects(rootNode);

            setRootCatalog(catalogService.getRootCatalog("objectsRootCatalog", getHome().getSmartHomeId()));
            objectsCatalogs.addAll(catalogService.getSubcatalogsRecursively(rootCatalog));
            Collections.sort(objectsCatalogs, new Comparator<Catalog>() {
                public int compare(final Catalog obj1, final Catalog obj2) {
                    return (obj1.getCatalogName().compareTo(obj2.getCatalogName()));
                }
            });
        }
        else {
            rootNode = null;
        }
    }

    private void reset() {
        objectsCatalogs = new ArrayList<Catalog>();
        rootCatalog = new Catalog();
        addedCatalog = new Catalog("New catalog", catalogService.getRootCatalog("objectsRootCatalog", getHome().getSmartHomeId()), getHome());
        setAdding(false);
    }

    public void addTaskInventory() {
        taskManager.addHomeTask(getHome().getSmartHomeId(), new HomeTask("GetInventory"));
        LOG.info("GetInventory: home=" + getHome().getSmartHomeId()+
                ".\n" + taskManager.getTaskMap().toString());
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
    }

    public void onSelectNode(OrganigramNodeSelectEvent event) {
        setSelectedObject((SmartObject)event.getOrganigramNode().getData());
        /*FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + selectedObject.getName() + "' selected." +
                "\nParent: " + selectedObject.getParentObject().getName());
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);*/
    }

    public void onClickContextMenu(OrganigramNodeSelectEvent event) {
        setSelectedObject((SmartObject)event.getOrganigramNode().getData());
        setSelectedObjectAlarms(alarmService.getRootAlarmsByObject(selectedObject.getSmartObjectId()));
        /*FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + selectedObject.getName() + "' selected. " + selectedObject.getCatalog().getCatalogName());
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);*/
    }

    public List<Alarm> getObjectAlarms() {
        if(selectedNode == null)
            return null;
        List<Alarm>  alarms = alarmService.getRootAlarmsByObject(((SmartObject)selectedNode.getData()).getSmartObjectId());
        return alarms;
    }

    public void saveChanges() {
        try {
            smartObjectService.updateInventory((SmartObject)selectedNode.getData());
            setAdding(false);
        } catch (Exception ex) {
            LOG.error("Error during saving:", ex);
            ContextUtils.addErrorSummaryToContext("Error during saving");
        }
    }

    public void setMaxSeverity() {
        AlarmSeverity severity = alarmService.getMaxSeverity(selectedObject.getSmartObjectId());
        if (severity == null)
            setSelectedMaxSeverity("");
        else
            setSelectedMaxSeverity(severity.name());
    }

    public String getStyleBySeverity(long smartObjectId) {
        AlarmSeverity maxSeverity = alarmService.getMaxSeverity(smartObjectId);
        if (maxSeverity == null)
            return "";
        switch (maxSeverity.ordinal()) {
            case 5:
                return "severity-critical";
            case 4:
                return "severity-major";
            case 3:
                return "severity-warn";
            case 2:
                return "severity-info";
            default:
                return "severity-normal";
        }
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

    public List<Catalog> getObjectsCatalogs() {
        return objectsCatalogs;
    }

    public void setObjectsCatalogs(List<Catalog> objectsCatalogs) {
        this.objectsCatalogs = objectsCatalogs;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public Catalog getRootCatalog() {
        return rootCatalog;
    }

    public void setRootCatalog(Catalog rootCatalog) {
        this.rootCatalog = rootCatalog;
    }


    public Catalog getAddedCatalog() {
        return addedCatalog;
    }

    public void setAddedCatalog(Catalog addedCatalog) {
        this.addedCatalog = addedCatalog;
    }

    public SmartObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(SmartObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public String getSelectedMaxSeverity() {
        return selectedMaxSeverity;
    }

    public void setSelectedMaxSeverity(String selectedMaxSeverity) {
        this.selectedMaxSeverity = selectedMaxSeverity;
    }
}
