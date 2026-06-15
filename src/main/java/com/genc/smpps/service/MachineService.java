package com.genc.smpps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.smpps.model.MachineLog;
import com.genc.smpps.repo.MachineLogRepository;

@Service
public class MachineService {

    @Autowired
    private MachineLogRepository repo;

    // recordRuntime()
    public MachineLog recordRuntime(MachineLog log) {
        log.setMachineStatus("RUNNING");
        return repo.save(log);
    }

    // logDowntime()
    public MachineLog logDowntime(MachineLog log) {
        log.setMachineStatus("BREAKDOWN");
        return repo.save(log);
    }

    public String getMachineOee() {
        List<MachineLog> logs = repo.findAll();
        double totalRuntime = 0;
        double totalDowntime = 0;
        for (MachineLog log : logs) {
            totalRuntime += log.getRuntimeHours();
            totalDowntime += log.getDowntimeHours();
        }
        if ((totalRuntime + totalDowntime) == 0) {
            return "No data available to calculate OEE";
        }
        double availability = totalRuntime / (totalRuntime + totalDowntime);
        double oee = availability * 100;
        return "OEE = " + String.format("%.2f", oee) + " %";
    }
    // getMachineStatus()
    public List<MachineLog> getAllLogs() {
        return repo.findAll();
    }
}
