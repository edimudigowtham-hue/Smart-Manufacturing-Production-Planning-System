package com.genc.smpps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.smpps.model.QualityInspection;
import com.genc.smpps.repo.QualityInspectionRepository;

@Service
public class QualityService {

    @Autowired
    private QualityInspectionRepository repo;

    // recordInspection + defect handling
    public QualityInspection recordInspection(QualityInspection q) {

        // Inspection logic (PDF requirement)
        if (q.getDefectCount() == 0) {
            q.setInspectionResult("PASS");
        } else if (q.getDefectCount() <= 3) {
            q.setInspectionResult("REWORK");
        } else {
            q.setInspectionResult("FAIL");
        }

        return repo.save(q);
    }

    //approveBatch()
    public String approveBatch(int id) {
        QualityInspection q = repo.findById(id).orElse(null);

        if (q != null) {
            q.setInspectionResult("PASS");
            repo.save(q);
            return "Batch approved";
        }

        return "Inspection not found";
    }

    //rejectBatch()
    public String rejectBatch(int id) {
        QualityInspection q = repo.findById(id).orElse(null);

        if (q != null) {
            q.setInspectionResult("FAIL");
            repo.save(q);
            return "Batch rejected";
        }

        return "Inspection not found";
    }

    //get all inspections
    public List<QualityInspection> getAllInspections() {
        return repo.findAll();
    }
}