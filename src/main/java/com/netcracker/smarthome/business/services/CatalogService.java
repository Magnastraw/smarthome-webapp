package com.netcracker.smarthome.business.services;

import com.netcracker.smarthome.dal.repositories.CatalogRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    
    @Autowired
    public  CatalogService (CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Transactional(readOnly = true)
    public List<Catalog> getSubcatalogs(Catalog catalog) {
        return this.catalogRepository.getSubcatalogs(catalog);
    }
    
    @Transactional
    public void deleteCatalog(Object primaryKey) {
        catalogRepository.delete(primaryKey);
    }
    
    @Transactional(readOnly = true)
    public Catalog getCatalogById(long catalogId) {
        return catalogRepository.get(catalogId);
    }
    
    @Transactional
    public void createCatalog(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    @Transactional(readOnly = true)
    public boolean checkCatalogName(String catalogName, long parentCatalogId) {
        return this.catalogRepository.checkCatalogName(catalogName, parentCatalogId);
    }

    @Transactional(readOnly = true)
    public List<Catalog> getPathToCatalog(Catalog catalog) {
        return catalogRepository.getPathToCatalog(catalog);
    }

    @Transactional(readOnly = true)
    public List<Catalog> getSubcatalogsRecursively(Catalog catalog) {
        return catalogRepository.getSubcatalogsRecursively(catalog);
    }

    @Transactional
    public Catalog updateCatalog(Catalog catalog) {
        return catalogRepository.update(catalog);
    }

    @Transactional(readOnly = true)
    public Catalog getRootCatalog(String catalogName, long smartHomeId) {
        return catalogRepository.getRootCatalog(catalogName, smartHomeId);
    }
}
