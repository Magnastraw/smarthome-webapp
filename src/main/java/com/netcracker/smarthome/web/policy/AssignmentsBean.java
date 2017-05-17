package com.netcracker.smarthome.web.policy;

import com.netcracker.smarthome.business.services.CatalogService;
import com.netcracker.smarthome.business.services.PolicyService;
import com.netcracker.smarthome.business.services.SmartObjectService;
import com.netcracker.smarthome.model.entities.Catalog;
import com.netcracker.smarthome.model.entities.Policy;
import com.netcracker.smarthome.model.entities.SmartObject;
import com.netcracker.smarthome.web.home.CurrentUserHomesBean;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ManagedBean
@ViewScoped
public class AssignmentsBean {
    private TreeNode policiesRoot;
    private TreeNode objectsRoot;
    private TreeNode selectedObject;
    private TreeNode[] selectedPolicies;
    private Map<Policy, TreeNode> policiesMap;

    @ManagedProperty(value = "#{policyService}")
    private PolicyService policyService;
    @ManagedProperty(value = "#{catalogService}")
    private CatalogService catalogService;
    @ManagedProperty(value = "#{smartObjectService}")
    private SmartObjectService objectService;
    @ManagedProperty(value = "#{currentUserHomesBean}")
    private CurrentUserHomesBean userHomesBean;

    public AssignmentsBean() {
        policiesMap = new HashMap<>();
    }

    @PostConstruct
    public void initialize() {
        createPoliciesTree();
        createObjectsTree();
    }

    public void createPoliciesTree() {
        policiesRoot = createPoliciesSubtree(catalogService.getRootCatalog("policiesRootCatalog", userHomesBean.getCurrentHome().getSmartHomeId()), null);
    }

    public void createObjectsTree() {
        SmartObject rootController = objectService.getRootController(userHomesBean.getCurrentHome().getSmartHomeId());
        if (rootController != null) {
            objectsRoot = createObjectsSubtree(rootController, null);
            objectsRoot.getChildren().get(0).setSelected(true);
        } else
            objectsRoot = new DefaultTreeNode("Root");
    }

    private TreeNode createObjectsSubtree(SmartObject subtreeRootObject, TreeNode parentNode) {
        List<SmartObject> children = objectService.getSubobjectsByObjectId(subtreeRootObject.getSmartObjectId());
        TreeNode subtreeRoot = new DefaultTreeNode(subtreeRootObject.getObjectType().getName().toLowerCase(), subtreeRootObject, parentNode);
        for (SmartObject childObject : children) {
            createObjectsSubtree(childObject, subtreeRoot);
        }
        return subtreeRoot;
    }

    private TreeNode createPoliciesSubtree(Catalog subtreeRootCatalog, TreeNode parentNode) {
        TreeNode subtreeRoot = new CheckboxTreeNode("catalog", subtreeRootCatalog, parentNode);
        subtreeRoot.setSelectable(false);
        List<Catalog> childCatalogs = catalogService.getSubcatalogs(subtreeRootCatalog);
        List<Policy> childPolicies = policyService.getPoliciesByCatalog(subtreeRootCatalog);
        TreeNode childNode;
        for (Policy policy : childPolicies) {
            childNode = new CheckboxTreeNode("policy", policy, subtreeRoot);
            policiesMap.put(policy, childNode);
        }
        for (Catalog catalog : childCatalogs) {
            createPoliciesSubtree(catalog, subtreeRoot);
        }
        return subtreeRoot;
    }

    public void onObjectSelect(NodeSelectEvent event) {
        selectedObject = event.getTreeNode();
        Set<Policy> assignedPolicies = policyService.getPoliciesByObject((SmartObject) event.getTreeNode().getData());
        for (Policy policy : policiesMap.keySet()) {
            policiesMap.get(policy).setSelected(assignedPolicies.contains(policy));
            policiesMap.get(policy).getParent().setExpanded(false);
        }
        for (Policy policy : assignedPolicies) {
            policiesMap.get(policy).getParent().setExpanded(true);
        }
    }

    public void onPolicySelect(NodeSelectEvent event) {
        if (selectedObject != null)
            policyService.saveAssignment((SmartObject) selectedObject.getData(), (Policy) event.getTreeNode().getData());
    }

    public void onPolicyUnselect(NodeUnselectEvent event) {
        if (selectedObject != null)
            policyService.deleteAssignment((SmartObject) selectedObject.getData(), (Policy) event.getTreeNode().getData());
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void setPolicyService(PolicyService policyService) {
        this.policyService = policyService;
    }

    public TreeNode getPoliciesRoot() {
        return policiesRoot;
    }

    public void setPoliciesRoot(TreeNode policiesRoot) {
        this.policiesRoot = policiesRoot;
    }

    public TreeNode getObjectsRoot() {
        return objectsRoot;
    }

    public void setObjectsRoot(TreeNode objectsRoot) {
        this.objectsRoot = objectsRoot;
    }

    public void setObjectService(SmartObjectService objectService) {
        this.objectService = objectService;
    }

    public void setUserHomesBean(CurrentUserHomesBean userHomesBean) {
        this.userHomesBean = userHomesBean;
    }

    public TreeNode getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(TreeNode selectedObject) {
        this.selectedObject = selectedObject;
    }

    public TreeNode[] getSelectedPolicies() {
        return selectedPolicies;
    }

    public void setSelectedPolicies(TreeNode[] selectedPolicies) {
        this.selectedPolicies = selectedPolicies;
    }
}
