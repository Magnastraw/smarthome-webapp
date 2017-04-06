package com.netcracker.smarthome.business.specs;

import com.netcracker.smarthome.dal.repositories.CatalogRepository;
import com.netcracker.smarthome.model.entities.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Transactional
    public List<Catalog> getSubcatalogs(Catalog catalog) {
        return this.catalogRepository.getSubcatalogs(catalog);
    }

    @Transactional
    public void deleteCatalog(Object primaryKey) {
        catalogRepository.delete(primaryKey);
    }

    @Transactional
    public Catalog getCatalogById(long catalogId) {
        return catalogRepository.get(catalogId);
    }

    @Transactional
    public void createCatalog(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    @Transactional
    public boolean checkCatalogName(String catalogName, long parentCatalogId) {
        return this.catalogRepository.checkCatalogName(catalogName, parentCatalogId);
    }

    @Transactional
    public List<Catalog> getPathToCatalog(Catalog catalog) {
        return catalogRepository.getPathToCatalog(catalog);
    }

    @Transactional
    public List<Catalog> getSubcatalogsRecursively(Catalog catalog) {
        return catalogRepository.getSubcatalogsRecursively(catalog);
    }

    @Transactional
    public List<Catalog> getSubcatalogsRecursively(Catalog rootCatalog, Catalog explicitCatalogTreeRoot) {
        return catalogRepository.getSubcatalogsRecursively(rootCatalog, explicitCatalogTreeRoot);
    }

    @Transactional
    public Catalog updateCatalog(Catalog catalog) {
        return catalogRepository.update(catalog);
    }

    @Transactional
    public Catalog getRootCatalog(String catalogName, long smartHomeId) {
        return catalogRepository.getRootCatalog(catalogName, smartHomeId);
    }
}
