package com.netcracker.smarthome.web.specs;

import com.netcracker.smarthome.business.specs.CatalogService;
import com.netcracker.smarthome.business.specs.AlarmSpecService;
import com.netcracker.smarthome.model.entities.AlarmSpec;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name="alarmSpecsBean")
@SessionScoped
public class AlarmSpecsBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(AlarmSpecsBean.class);
    private List<TableEntity> tableEntities;
    private TableEntity editedTableEntity;
    private List<TableEntity> filteredTableEntities;
    private List<Catalog> allCatalogs;
    private List<Catalog> currentCatalogs;
    private List<AlarmSpec> alarmSpecs;
    private List<Catalog> menuCatalogs;
    private TableEntity removed;
    private boolean creatingMode;
    private Catalog root;
    private String currentName;

    @ManagedProperty(value = "#{catalogService}")
    private CatalogService catalogService;
    
    @ManagedProperty(value = "#{alarmSpecService}")
    private AlarmSpecService alarmSpecService;

    @PostConstruct
    public void init() {
        tableEntities = new ArrayList<TableEntity>();
        menuCatalogs = new ArrayList<Catalog>();
        getRootCatalogs();
        List<Catalog> rootCatalogs = catalogService.getRootAlarmSpecsCatalogs(((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome().getSmartHomeId());
        allCatalogs = new ArrayList<Catalog>();
        allCatalogs.addAll(rootCatalogs);
        for (Catalog root : rootCatalogs) {
            allCatalogs.addAll(catalogService.getSubcatalogsRecursively(root));
        }
        Collections.sort(allCatalogs, new Comparator<Catalog>() {
            public int compare(final Catalog obj1, final Catalog obj2) {
                return (obj1.getCatalogName().compareTo(obj2.getCatalogName()));
            }
        });
        creatingMode = true;
        editedTableEntity = getDefaultTableEntity();
        root = catalogService.getCatalogById(catalogService.getRootCatalogId("alarmSpecsRootCatalog", ((CurrentUserHomesBean)ContextUtils.getBean("currentUserHomesBean")).getCurrentHome().getSmartHomeId()));
    }

    public void getRootCatalogs() {
        clearMenuCatalogs();
        currentCatalogs = catalogService.getRootAlarmSpecsCatalogs(((CurrentUserHomesBean)ContextUtils.getBean("currentUserHomesBean")).getCurrentHome().getSmartHomeId());
        tableEntities.clear();
        for (Catalog c : currentCatalogs) {
            tableEntities.add(new TableEntity(c, c.getCatalogName(), true));
        }
    }

    public void expandCatalog(Catalog catalog) {
        if (catalog != null) {
            LOG.info("expandCatalog: " + catalog.getCatalogName());
            tableEntities.clear();
            clearMenuCatalogs();
            menuCatalogs = catalogService.getPathToCatalog(catalog);
            currentCatalogs = catalogService.getSubcatalogs(catalog);
            if (currentCatalogs.size() != 0) {
                for (Catalog c : currentCatalogs) {
                    tableEntities.add(new TableEntity(c, c.getCatalogName(), true));
                }
            }
            alarmSpecs = alarmSpecService.getAlarmSpecs(catalog);
            if (alarmSpecs.size() != 0) {
                for (AlarmSpec as : alarmSpecs) {
                    tableEntities.add(new TableEntity(as, as.getSpecName(), false));
                }
            }
        }
        else {
            getRootCatalogs();
        }
    }

    private void clearMenuCatalogs() {
        menuCatalogs.clear();
    }

    public void onSelect(TableEntity folder) {
        if (folder.isTypeCatalog()) {
            expandCatalog(folder.getCatalog());
        }
    }

    public void remove() {
        try {
            if (removed.isTypeCatalog()) {
                Catalog removedCatalog = removed.getCatalog();
                catalogService.deleteCatalog(removedCatalog.getCatalogId());
                expandCatalog(removedCatalog.getParentCatalog());
                allCatalogs.remove(removedCatalog);
            } else {
                AlarmSpec removedAlarmSpec = removed.getAlarmSpec();
                alarmSpecService.deleteAlarmSpec(removedAlarmSpec.getSpecId());
                expandCatalog(removedAlarmSpec.getCatalog());
            }
        }
        catch (Exception ex) {
            LOG.error("Error during deleting: ", ex);
        }
    }

    public void saveChanges() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean check = true;
        if (editedTableEntity.getName() == "") {
            ContextUtils.addErrorSummaryToContext("The name must be not empty");
            check = false;
        }
        if (!editedTableEntity.isTypeCatalog()) {
            if (editedTableEntity.getAlarmSpec().getObjectType() == "") {
                ContextUtils.addErrorSummaryToContext("The object type must be not empty");
                check = false;
            }
        }
        if (!check) {
            context.addCallbackParam("correct", false);
        }
        else {
            if (editedTableEntity.isTypeCatalog()) {
                try {
                    Catalog catalog = editedTableEntity.getCatalog();
                    catalog.setCatalogName(editedTableEntity.getName());
                    catalog.setSmartHome(((CurrentUserHomesBean) ContextUtils.getBean("currentUserHomesBean")).getCurrentHome());
                    check = false;
                    if (catalog.getCatalogName().equals(currentName)) {
                        check = true;
                    }
                    else {
                        if (!catalogService.checkCatalogName(catalog.getCatalogName(), catalog.getParentCatalog().getCatalogId())) {
                            check = false;
                            editedTableEntity.setName(currentName);
                        } else {
                            check = true;
                        }
                    }
                    if (!check) {
                        ContextUtils.addErrorSummaryToContext("The given name exists in this directory");
                        context.addCallbackParam("correct", false);
                    }
                    else {
                        if (creatingMode) {
                            catalogService.createCatalog(catalog);
                            allCatalogs.add(catalog);
                            Collections.sort(allCatalogs, new Comparator<Catalog>() {
                                public int compare(final Catalog obj1, final Catalog obj2) {
                                    return (obj1.getCatalogName().compareTo(obj2.getCatalogName()));
                                }
                            });
                        }
                        else {
                            catalogService.updateCatalog(catalog);
                            for (int i = 0; i < allCatalogs.size(); i++) {
                                if (allCatalogs.get(i).getCatalogId() == catalog.getCatalogId())
                                    allCatalogs.set(i, catalog);
                            }
                        }
                        expandCatalog(getCurrentCatalog());
                        setCurrentName("");
                        context.addCallbackParam("correct", true);
                    }
                }
                catch (Exception ex) {
                    LOG.error("Error during saving:", ex);
                    ContextUtils.addErrorSummaryToContext("Error during saving changes!");
                    context.addCallbackParam("correct", false);
                }
            } else {
                try {
                    AlarmSpec alarmSpec = editedTableEntity.getAlarmSpec();
                    alarmSpec.setSpecName(editedTableEntity.getName());
                    check = false;
                    if (alarmSpec.getSpecName().equals(currentName)) {
                        check = true;
                    } else {
                        if (!alarmSpecService.checkAlarmSpecName(alarmSpec.getSpecName(), alarmSpec.getCatalog().getCatalogId())) {
                            check = false;
                            editedTableEntity.setName(currentName);
                        } else {
                            check = true;
                        }
                    }
                    if (!check) {
                        ContextUtils.addErrorSummaryToContext("The given name exists in this directory");
                        context.addCallbackParam("correct", false);
                    } else {
                        if (creatingMode) {
                            alarmSpecService.createAlarmSpec(alarmSpec);
                        } else {
                            alarmSpecService.updateAlarmSpec(alarmSpec);
                        }
                        expandCatalog(getCurrentCatalog());
                        setCurrentName("");
                        context.addCallbackParam("correct", true);
                    }
                } catch (Exception ex) {
                    LOG.error("Error during saving:", ex);
                    ContextUtils.addErrorSummaryToContext("Error during saving changes!");
                    context.addCallbackParam("correct", false);
                }
            }
        }
    }

    public void setMode(boolean creatingMode) {
        setCreatingMode(creatingMode);
        setEditedTableEntity(getDefaultTableEntity());
        setCurrentName("");
    }

    private SmartHome getHome() {
        return ((CurrentUserHomesBean)ContextUtils.getBean("currentUserHomesBean")).getCurrentHome();
    }

    private Catalog getCurrentCatalog() {
        Catalog currentCatalog = new Catalog();
        try {
            if (menuCatalogs.size() != 0) {
                currentCatalog = menuCatalogs.get(menuCatalogs.size() - 1);
            } else {
                currentCatalog = root;
            }
        } catch (Exception ex) {
            LOG.error("Error:", ex);
        }
        return currentCatalog;
    }

    public TableEntity getDefaultTableEntity() {
        return new TableEntity(new Catalog("", getCurrentCatalog(), getHome()), new AlarmSpec(getCurrentCatalog()), "", true);
    }

    public void setCurrentTableEntity (TableEntity current) {
        setCurrentName(current.getName());
        setCreatingMode(false);
        setEditedTableEntity(current);
    }

    public int sortByName(Object obj1, Object obj2){
        return Sort.sortByName(obj1, obj2);
    }

    public int sortByObjectType(Object obj1, Object obj2) {
        return Sort.sortByObjectType(obj1, obj2);
    }

    public boolean filterByObjectType(Object value, Object filter, Locale locale) {
        return Filter.filterByObjectType(value, filter);
    }

    /*setters and getters*/
    public List<TableEntity> getTableEntities() {
        return tableEntities;
    }

    public void setTableEntities(List<TableEntity> tableEntities) {
        this.tableEntities = tableEntities;
    }

    public List<Catalog> getAllCatalogs() {
        return allCatalogs;
    }

    public void setAllCatalogs(List<Catalog> allCatalogs) {
        this.allCatalogs = allCatalogs;
    }

    public List<Catalog> getCurrentCatalogs() {
        return currentCatalogs;
    }

    public void setCurrentCatalogs(List<Catalog> currentCatalogs) {
        this.currentCatalogs = currentCatalogs;
    }

    public List<AlarmSpec> getAlarmSpecs() {
        return alarmSpecs;
    }

    public void setAlarmSpecs(List<AlarmSpec> alarmSpecs) {
        this.alarmSpecs = alarmSpecs;
    }

    public List<Catalog> getMenuCatalogs() {
        return menuCatalogs;
    }

    public void setMenuCatalogs(List<Catalog> menuCatalogs) {
        this.menuCatalogs = menuCatalogs;
    }

    public List<TableEntity> getFilteredTableEntities() {
        return filteredTableEntities;
    }

    public void setFilteredTableEntities(List<TableEntity> filteredTableEntities) {
        this.filteredTableEntities = filteredTableEntities;
    }

    public TableEntity getRemoved() {
        return this.removed;
    }

    public void setRemoved(TableEntity removed) {
        this.removed = removed;
    }

    public boolean getCreatingMode() {
        return creatingMode;
    }

    public void setCreatingMode(boolean creatingMode) {
        this.creatingMode = creatingMode;
    }

    public TableEntity getEditedTableEntity() {
        return editedTableEntity;
    }

    public void setEditedTableEntity(TableEntity editedTableEntity) {
        this.editedTableEntity = editedTableEntity;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void setAlarmSpecService(AlarmSpecService alarmSpecService) {
        this.alarmSpecService = alarmSpecService;
    }

    public Catalog getRoot() {
        return root;
    }

    public void setRoot(Catalog root) {
        this.root = root;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}