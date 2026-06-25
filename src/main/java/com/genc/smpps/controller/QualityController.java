package com.genc.smpps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.genc.smpps.model.QualityInspection;
import com.genc.smpps.service.QualityService;

import jakarta.validation.Valid;
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

    // Record inspection with validation
    @PostMapping("/inspect")
    public ResponseEntity<?> inspect(@RequestBody @Valid QualityInspection q, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return ResponseEntity.ok(service.recordInspection(q));
    }

    // Approve batch
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approve(@PathVariable int id) {
        return ResponseEntity.ok(service.approveBatch(id));
    }

    // Reject batch
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable int id) {
        return ResponseEntity.ok(service.rejectBatch(id));
    }
}
