package com.genc.smpps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.genc.smpps.model.QualityInspection;
import com.genc.smpps.service.QualityService;

import java.util.List;

@RestController
@RequestMapping("/quality")
public class QualityController {

    @Autowired
    private QualityService service;

    // Get all inspections
    @GetMapping("/inspections")
    public List<QualityInspection> getAllInspections() {
        return service.getAllInspections();
    }

    // Record inspection
    @PostMapping("/inspect")
    public QualityInspection inspect(@RequestBody QualityInspection q) {
        return service.recordInspection(q);
    }

    // Approve batch
    @PutMapping("/approve/{id}")
    public String approve(@PathVariable int id) {
        return service.approveBatch(id);
    }

    // Reject batch
    @PutMapping("/reject/{id}")
    public String reject(@PathVariable int id) {
        return service.rejectBatch(id);
    }
}
