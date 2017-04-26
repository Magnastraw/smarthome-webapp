package com.netcracker.smarthome.web.specs;

import com.netcracker.smarthome.business.services.UnitService;
import com.netcracker.smarthome.model.entities.Unit;
import com.netcracker.smarthome.web.common.ContextUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ManagedBean(name="unitsBean")
@SessionScoped
public class UnitsBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(UnitsBean.class);
    private List<Unit> units;
    private List<Unit> filteredUnits;
    private List<BigInteger> baseFactors;
    private Unit editedUnit;
    private Unit removedUnit;
    private boolean creatingMode;
    private String currentName;

    @ManagedProperty(value = "#{unitService}")
    private UnitService unitService;

    @PostConstruct
    public void init() {
        units = unitService.getUnits();
        baseFactors = unitService.getBaseFactors();
        creatingMode = true;
        editedUnit = getDefaultUnitValue();
    }

    public void removeUnit() {
        try {
            unitService.deleteUnit(removedUnit.getUnitId());
            units.remove(removedUnit);
        } catch (Exception ex) {
            LOG.error("Error during deleting: ", ex);
        }
    }

    public void saveChanges() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean check = true;
        if (editedUnit.getUnitName() == "") {
            ContextUtils.addErrorSummaryToContext("The name must be not empty");
            check = false;
        }
        if (editedUnit.getBaseFactor() == null) {
            ContextUtils.addErrorSummaryToContext("The base factor must be not empty");
            check = false;
        }
        if (editedUnit.getLabel() == "") {
            ContextUtils.addErrorSummaryToContext("The label must be not empty");
            check = false;
        }
        if (!check) {
            context.addCallbackParam("correct", false);
        }
        else {
            try {
                check = false;
                if (editedUnit.getUnitName().equals(currentName)) {
                    check = true;
                }
                else {
                    if (!unitService.checkUnitName(editedUnit.getUnitName())) {
                        check = false;
                        editedUnit.setUnitName(currentName);
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
                        unitService.createUnit(editedUnit);
                        units.add(editedUnit);
                        Collections.sort(units, new Comparator<Unit>() {
                            public int compare(final Unit obj1, final Unit obj2) {
                                return (obj1.getUnitName().compareTo(obj2.getUnitName()));
                            }
                        });
                    } else {
                        unitService.updateUnit(editedUnit);
                        for (int i = 0; i < units.size(); i++) {
                            if (units.get(i).getUnitId() == editedUnit.getUnitId())
                                units.set(i, editedUnit);
                        }
                    }
                    setCurrentName("");
                    context.addCallbackParam("correct", true);
                }
            } catch (Exception ex) {
                LOG.error("Error during saving: ", ex);
                ContextUtils.addErrorSummaryToContext("Error during saving changes!");
                context.addCallbackParam("correct", false);
            }
        }
    }

    public void setCurrentUnit(Unit current) {
        setCurrentName(current.getUnitName());
        setCreatingMode(false);
        setEditedUnit(current);
    }

    public Unit getDefaultUnitValue() {
        return new Unit("",null,"", new Unit());
    }

    public void setMode(boolean creatingMode) {
        setCreatingMode(creatingMode);
        setEditedUnit(getDefaultUnitValue());
        setCurrentName("");
    }

    /*setters and getters*/
    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Unit> getFilteredUnits() {
        return filteredUnits;
    }

    public void setFilteredUnits(List<Unit> filteredUnits) {
        this.filteredUnits = filteredUnits;
    }

    public List<BigInteger> getBaseFactors() {
        return baseFactors;
    }

    public void setBaseFactors(List<BigInteger> baseFactors) {
        this.baseFactors = baseFactors;
    }

    public Unit getEditedUnit() {
        return editedUnit;
    }

    public void setEditedUnit(Unit editedUnit) {
        this.editedUnit = editedUnit;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public Unit getRemovedUnit() {
        return removedUnit;
    }

    public void setRemovedUnit(Unit removedUnit) {
        this.removedUnit = removedUnit;
    }

    public boolean getCreatingMode() {
        return creatingMode;
    }

    public void setCreatingMode(boolean creatingMode) {
        this.creatingMode = creatingMode;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
