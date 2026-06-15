package com.genc.smpps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.genc.smpps.model.FinishedProduct;
import com.genc.smpps.repo.ProductRepository;

@Service
public class ProductMasterService {

    @Autowired
    private ProductRepository repo;

    public FinishedProduct createProduct(FinishedProduct product) {
        return repo.save(product);
    }

    public FinishedProduct updateBomVersion(int id, String version) {
        FinishedProduct p = repo.findById(id).orElse(null);
        if (p != null) {
            p.setBomVersion(version);
            return repo.save(p);
        }
        return null;
    }

    public List<FinishedProduct> getAllProducts() {
        return repo.findAll();
    }

    // Placeholder methods (from PDF)
    public String addBomComponent() {
        return "Component added (mock)";
    }

    public FinishedProduct getProductStructure(int id) {
        return repo.findById(id).orElse(null);
    }
    // Delete
    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    // Get product by ID
    public FinishedProduct getProductById(int id) {
        return repo.findById(id).orElse(null);
    }
}