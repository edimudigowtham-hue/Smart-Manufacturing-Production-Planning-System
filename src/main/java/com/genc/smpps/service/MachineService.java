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
    
}
