package com.netcracker.smarthome.web.specs;

import com.netcracker.smarthome.business.services.CatalogService;
import com.netcracker.smarthome.business.services.MetricSpecService;
import com.netcracker.smarthome.business.services.UnitService;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.MetricSpec;
import com.netcracker.smarthome.model.entities.SmartHome;
import com.netcracker.smarthome.model.entities.Unit;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import com.netcracker.smarthome.web.specs.table.Filter;
import com.netcracker.smarthome.web.specs.table.Sort;
import com.netcracker.smarthome.web.specs.table.TableEntity;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name="metricSpecsBean")
@SessionScoped
public class MetricSpecsBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(MetricSpecsBean.class);
    private List<TableEntity> tableEntities;
    private TableEntity editedTableEntity;
    private List<TableEntity> filteredTableEntities;
    private List<Catalog> allCatalogs;
    private List<Catalog> currentCatalogs;
    private List<MetricSpec> metricSpecs;
    private List<Catalog> menuCatalogs;
    private TableEntity removed;
    private List<Unit> units;
    private boolean creatingMode;
    private Catalog root;
    private String currentName;
    
    @ManagedProperty(value = "#{catalogService}")
    private CatalogService catalogService;
    @ManagedProperty(value = "#{metricSpecService}")
    private MetricSpecService metricSpecService;
    @ManagedProperty(value = "#{unitService}")
    private UnitService unitService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;

    @PostConstruct
    public void init() {
        changeCurrentHome();
    }

    public void changeCurrentHome() {
        root = catalogService.getRootCatalog("metricSpecsRootCatalog", getHome().getSmartHomeId());
        tableEntities = new ArrayList<TableEntity>();
        menuCatalogs = new ArrayList<Catalog>();
        units = unitService.getUnits();
        List<Catalog> rootCatalogs = catalogService.getSubcatalogs(root);
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
        expandCatalog(root);
    }

   /* public void getRootCatalogs() {
        clearMenuCatalogs();
        currentCatalogs = catalogService.getSubcatalogs(root);
        tableEntities.clear();
        for (Catalog c : currentCatalogs) {
            tableEntities.add(new TableEntity(c, c.getCatalogName(), true));
        }
        metricSpecs = metricSpecService.getMetricSpecs(root);
        if (metricSpecs.size() != 0) {
            for (MetricSpec ms : metricSpecs) {
                tableEntities.add(new TableEntity(ms, ms.getSpecName(), false));
            }
        }
    }*/

    public void expandCatalog(Catalog catalog) {
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
        metricSpecs = metricSpecService.getMetricSpecs(catalog);
        if (metricSpecs.size() != 0) {
            for (MetricSpec ms : metricSpecs) {
                tableEntities.add(new TableEntity(ms, ms.getSpecName(), false));
            }
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
                MetricSpec removedMetricSpec = removed.getMetricSpec();
                metricSpecService.deleteMetricSpec(removedMetricSpec.getSpecId());
                expandCatalog(removedMetricSpec.getCatalog());
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
            if (editedTableEntity.getMetricSpec().getMetricType() == "") {
                ContextUtils.addErrorSummaryToContext("The metric type must be not empty");
                check = false;
            }
        }
        if (!check) {
            context.addCallbackParam("correct", false);
        }
        else {
            if (editedTableEntity.isTypeCatalog()) {
                try {
                    editedTableEntity.getCatalog().setCatalogName(editedTableEntity.getName());
                    editedTableEntity.getCatalog().setParentCatalog(getCurrentCatalog());
                    //editedTableEntity.getCatalog().setSmartHome(getHome());
                    Catalog catalog = editedTableEntity.getCatalog();
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
                    MetricSpec metricSpec = editedTableEntity.getMetricSpec();
                    metricSpec.setSpecName(editedTableEntity.getName());
                    metricSpec.setCatalog(getCurrentCatalog());
                    check = false;
                    if (metricSpec.getSpecName().equals(currentName)) {
                        check = true;
                    }
                    else {
                        if (!metricSpecService.checkMetricName(metricSpec.getSpecName(), metricSpec.getCatalog().getCatalogId())) {
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
                            metricSpecService.createMetricSpec(metricSpec);
                        } else {
                            metricSpecService.updateMetricSpec(metricSpec);
                        }
                        expandCatalog(getCurrentCatalog());
                        setCurrentName("");
                        context.addCallbackParam("correct", true);
                    }
                } catch (Exception ex) {
                    LOG.error("Error during saving:", ex);
                    ContextUtils.addErrorSummaryToContext("Error during saving changes");
                    context.addCallbackParam("correct", false);
                }
            }
        }
    }

    public void move() {
        try {
            if (editedTableEntity.isTypeCatalog()) {
                catalogService.updateCatalog(editedTableEntity.getCatalog());
            }
            else {
                metricSpecService.updateMetricSpec(editedTableEntity.getMetricSpec());
            }
            expandCatalog(getCurrentCatalog());
        } catch (Exception ex) {
            LOG.error("Error during moving:", ex);
            ContextUtils.addErrorSummaryToContext("Error during moving");
        }
    }

    public void setMode(boolean creatingMode) {
        setCreatingMode(creatingMode);
        setEditedTableEntity(getDefaultTableEntity());
        setCurrentName("");
    }

    private SmartHome getHome() {
        return userHomesBean.getCurrentHome();
    }

    private Catalog getCurrentCatalog() {
        Catalog currentCatalog = new Catalog();
        try {
            if (menuCatalogs.size() != 0) {
                currentCatalog = menuCatalogs.get(menuCatalogs.size() - 1);
            }
            else {
                currentCatalog = root;
            }
        } catch (Exception ex) {
            LOG.error("Error:", ex);
        }
        return currentCatalog;
    }

    private TableEntity getDefaultTableEntity() {
        return new TableEntity(new Catalog("", getCurrentCatalog(), getHome()), new MetricSpec(getCurrentCatalog(), new Unit()), "", true);
    }

    public void setCurrentTableEntity (TableEntity current) {
        setCurrentName(current.getName());
        setCreatingMode(false);
        setEditedTableEntity(current);
    }

    public int sortByName(Object obj1, Object obj2){
        return Sort.sortByName(obj1, obj2);
    }

    public int sortByMetricType(Object obj1, Object obj2) {
        return Sort.sortByMetricType(obj1, obj2);
    }

    public int sortByUnit(Object obj1, Object obj2) {
        return Sort.sortByUnit(obj1, obj2);
    }

    public int sortByMinValue(Object obj1, Object obj2) {
        return Sort.sortByMinValue(obj1, obj2);
    }

    public int sortByMaxValue(Object obj1, Object obj2) {
        return Sort.sortByMaxValue(obj1, obj2);
    }

    public int sortByAssigned(Object obj1, Object obj2) {
        return Sort.sortByAssigned(obj1, obj2);
    }

    public boolean filterByMetricType(Object value, Object filter, Locale locale) {
        return Filter.filterByMetricType(value, filter);
    }

    public boolean filterByUnit(Object value, Object filter, Locale locale) {
        return Filter.filterByUnit(value, filter);
    }

    public boolean filterByMinValue(Object value, Object filter, Locale locale) {
        return Filter.filterByMinValue(value, filter);
    }

    public boolean filterByMaxValue(Object value, Object filter, Locale locale) {
        return Filter.filterByMaxValue(value, filter);
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

    public List<MetricSpec> getMetricSpecs() {
        return metricSpecs;
    }

    public void setMetricSpecs(List<MetricSpec> metricSpecs) {
        this.metricSpecs = metricSpecs;
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

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
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

    public void setMetricSpecService(MetricSpecService metricSpecService) {
        this.metricSpecService = metricSpecService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
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

    public void setValue() {
        editedTableEntity.setName(currentName);
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }
}