package com.genc.smpps.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.genc.smpps.model.MachineLog;

public interface MachineLogRepository extends JpaRepository<MachineLog, Integer> {
}