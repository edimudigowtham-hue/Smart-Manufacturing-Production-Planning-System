package com.genc.smpps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.smpps.model.MaintenanceWorkOrder;
import com.genc.smpps.repo.MaintenanceWorkOrderRepository;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceWorkOrderRepository repo;

    //createWorkOrder
    public MaintenanceWorkOrder createWorkOrder(MaintenanceWorkOrder w) {
        w.setWorkOrderStatus("OPEN"); // default status
        return repo.save(w);
    }

    // assignTechnician
    public MaintenanceWorkOrder assignTechnician(int id, String technician) {

        MaintenanceWorkOrder w = repo.findById(id).orElse(null);

        if (w != null) {
            w.setTechnician(technician);
            w.setWorkOrderStatus("IN_PROGRESS");
            return repo.save(w);
        }

        return null;
    }

    // issueSpare
    public MaintenanceWorkOrder issueSpare(int id, String spareParts) {

        MaintenanceWorkOrder w = repo.findById(id).orElse(null);

        if (w != null) {
            w.setSpareParts(spareParts);
            return repo.save(w);
        }

        return null;
    }

    // closeWorkOrder
    public MaintenanceWorkOrder closeWorkOrder(int id) {

        MaintenanceWorkOrder w = repo.findById(id).orElse(null);

        if (w != null) {
            w.setWorkOrderStatus("COMPLETED");
            return repo.save(w);
        }

        return null;
    }

    // get all work orders (for UI)
    public List<MaintenanceWorkOrder> getAllWorkOrders() {
        return repo.findAll();
    }
}