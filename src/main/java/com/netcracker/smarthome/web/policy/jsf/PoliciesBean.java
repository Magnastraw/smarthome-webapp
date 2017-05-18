package com.netcracker.smarthome.web.policy.jsf;

import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.business.services.CatalogService;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.enums.PolicyStatus;
import com.netcracker.smarthome.web.common.ContextUtils;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import java.util.LinkedList;
import java.util.List;

@ManagedBean
@SessionScoped
public class PoliciesBean {
    private List<Catalog> catalogPath;
    private List<PoliciesTableItem> tableItems;
    private Catalog rootCatalog;
    private PolicyStatus[] statuses;
    private PoliciesTableItem selectedItem;
    private Catalog selectedCatalog;
    @ManagedProperty(value = "#{catalogService}")
    private CatalogService catalogService;
    @ManagedProperty(value = "#{policyService}")
    private PolicyService policyService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;

    public PoliciesBean() {
        statuses = PolicyStatus.values();
        catalogPath = new LinkedList<Catalog>();
        selectedItem = getDefaultPolicyItem();
    }

    @PostConstruct
    public void initialize() {
        rootCatalog = catalogService.getRootCatalog("policiesRootCatalog", userHomesBean.getCurrentHome().getSmartHomeId());
        changeCatalog(rootCatalog);
    }

    private Catalog getCurrentCatalog() {
        return catalogPath.isEmpty() ? rootCatalog : catalogPath.get(catalogPath.size() - 1);
    }

    private void resetCatalogItems(Catalog currentCatalog) {
        tableItems = new LinkedList<PoliciesTableItem>();
        List<Catalog> subcatalogs = catalogService.getSubcatalogs(currentCatalog);
        for (Catalog catalog : subcatalogs
                ) {
            tableItems.add(new CatalogItem(catalog));
        }
        List<Policy> policies = policyService.getPoliciesByCatalog(currentCatalog);
        for (Policy policy : policies
                ) {
            tableItems.add(new PolicyItem(policy));
        }
    }

    public void changeCatalog(Catalog catalog) {
        catalogPath = catalogService.getPathToCatalog(catalog);
        resetCatalogItems(catalog);
    }

    public void saveItem(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (selectedItem.isCatalog()) {
                Catalog catalogToSave = (Catalog) selectedItem.getItem();
                catalogToSave.setParentCatalog(selectedCatalog);
                selectedItem.setItem(catalogService.updateCatalog(catalogToSave));
            } else
                selectedItem.setItem(policyService.savePolicy((Policy) selectedItem.getItem()));
        } catch (Exception e) {
            ContextUtils.addErrorMessageToContext("Error during saving changes!");
            context.addCallbackParam("correct", false);
            return;
        }
        context.addCallbackParam("correct", true);
        resetCatalogItems(getCurrentCatalog());
    }

    public void deleteItem() {
        if (selectedItem.isCatalog()) {
            catalogService.deleteCatalog(((Catalog) selectedItem.getItem()).getCatalogId());
        } else
            policyService.deletePolicy(((Policy) selectedItem.getItem()).getPolicyId());
        resetCatalogItems(getCurrentCatalog());
    }

    public PolicyItem getDefaultPolicyItem() {
        return new PolicyItem(new Policy("New policy", PolicyStatus.CREATED, getCurrentCatalog(), ""));
    }

    public CatalogItem getDefaultCatalogItem() {
        return new CatalogItem(new Catalog("New catalog", getCurrentCatalog(), userHomesBean.getCurrentHome()));
    }

    public List<Catalog> getPossibleParentCatalogs() {
        if (selectedItem.isCatalog()) {
            selectedCatalog = ((Catalog) selectedItem.getItem()).getParentCatalog();
            return catalogService.getSubcatalogsRecursively(rootCatalog, (Catalog) selectedItem.getItem());
        }
        return catalogService.getSubcatalogsRecursively(rootCatalog);
    }

    public void dialogCatalogChanged(ValueChangeEvent event) {
        selectedCatalog = (Catalog) event.getNewValue();
    }

    public List<Catalog> getCatalogPath() {
        return catalogPath;
    }

    public void setCatalogPath(LinkedList<Catalog> catalogPath) {
        this.catalogPath = catalogPath;
    }

    public PolicyService getPolicyService() {
        return policyService;
    }

    public void setPolicyService(PolicyService policyService) {
        this.policyService = policyService;
    }

    public List<PoliciesTableItem> getTableItems() {
        return tableItems;
    }

    public void setTableItems(List<PoliciesTableItem> tableItems) {
        this.tableItems = tableItems;
    }

    public CurrentUserHomesBean getUserHomesBean() {
        return userHomesBean;
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }

    public CatalogService getCatalogService() {
        return catalogService;
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

    public PolicyStatus[] getStatuses() {
        return statuses;
    }

    public void setStatuses(PolicyStatus[] statuses) {
        this.statuses = statuses;
    }

    public PoliciesTableItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(PoliciesTableItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Catalog getSelectedCatalog() {
        return selectedCatalog;
    }

    public void setSelectedCatalog(Catalog selectedCatalog) {
        this.selectedCatalog = selectedCatalog;
    }
}
