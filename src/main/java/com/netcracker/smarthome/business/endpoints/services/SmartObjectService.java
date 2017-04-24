package com.netcracker.smarthome.business.endpoints.services;

import com.netcracker.smarthome.dal.repositories.*;
import com.netcracker.smarthome.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmartObjectService {

    @Autowired
    private SmartObjectRepository smartObjectRepository;

    @Autowired
    private ObjectTypeRepository objectTypeRepository;

    @Autowired
    private ObjectParamRepository objectParamRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Transactional(readOnly = true)
    public SmartObject getObjectById(long objectId) {
        return smartObjectRepository.get(objectId);
    }

    @Transactional(readOnly = true)
    public SmartObject getObjectByExternalKey(long smartHomeId, long externalKey) {
        return smartObjectRepository.getObjectByExternalKey(smartHomeId, externalKey);
    }

    @Transactional(readOnly = true)
    public ObjectType getObjectTypeByName(String name) {
        return objectTypeRepository.getByName(name.toLowerCase());
    }

    @Transactional
    public void saveInventory(SmartObject object) {
        smartObjectRepository.save(object);
    }

    @Transactional
    public void saveObjectParam(ObjectParam param) {
        objectParamRepository.save(param);
    }

    @Transactional(readOnly = true)
    public Catalog getRootCatalog(long smartHomeId) {
        return catalogRepository.getRootCatalog("objectsRootCatalog", smartHomeId);
    }

    @Transactional
    public void deleteObject(long smartObjectId) {
        smartObjectRepository.delete(smartObjectId);
    }

    @Transactional
    public void updateInventory(SmartObject object) {
        smartObjectRepository.update(object);
    }

    @Transactional
    public void updateObjectParam(ObjectParam param) {
        objectParamRepository.update(param);
    }

    @Transactional(readOnly = true)
    public ObjectParam getObjectParamByName(long smartObjectId, String paramName) {
        return objectParamRepository.getByName(smartObjectId, paramName.toLowerCase());
    }

    @Transactional(readOnly = true)
    public SmartObject getRootController(long smartObjectId) {
        return smartObjectRepository.getRootController(smartObjectId);
    }

    @Transactional(readOnly = true)
    public List<SmartObject> getSubobjectsByObjectId(long objectId) {
        return smartObjectRepository.getSubobjectsByObjectId(objectId);
    }

    @Transactional(readOnly = true)
    public List<String> getObjectTypes() {
        return objectTypeRepository.getObjectTypes();
    }

}
