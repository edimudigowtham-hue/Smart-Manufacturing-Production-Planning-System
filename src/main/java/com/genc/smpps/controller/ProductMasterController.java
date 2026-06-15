package com.genc.smpps.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.genc.smpps.model.FinishedProduct;
import com.genc.smpps.service.ProductMasterService;

@Controller
@RequestMapping("/products")
public class ProductMasterController {

    @Autowired
    private ProductMasterService service;

    @PostMapping("/createProduct")
    public String createProduct(@ModelAttribute FinishedProduct product) {
        service.createProduct(product);
        return "redirect:/products-page";
    }

    @PostMapping("/updateBom")
    public String updateBom(@RequestParam int productId,
                            @RequestParam String bomVersion) {

        service.updateBomVersion(productId, bomVersion);
        return "redirect:/products-page";
    }

    @PutMapping("/updateBomVersion/{id}/{version}")
    public FinishedProduct updateBomVersion(@PathVariable int id, @PathVariable String version) {
        return service.updateBomVersion(id, version);
    }

    @GetMapping("/all")
    public List<FinishedProduct> getProducts() {
        return service.getAllProducts();
    }
}