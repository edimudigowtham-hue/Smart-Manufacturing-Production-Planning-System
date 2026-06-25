package com.genc.smpps.model;

import jakarta.persistence.*;

@Entity
public class MachineLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    private int machineId;
    private String logDate;
    private double runtimeHours;
    private double downtimeHours;
    private String downtimeReason;
    private String machineStatus; // RUNNING, IDLE, BREAKDOWN, MAINTENANCE

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public double getRuntimeHours() {
        return runtimeHours;
    }

    public void setRuntimeHours(double runtimeHours) {
        this.runtimeHours = runtimeHours;
    }

    public double getDowntimeHours() {
        return downtimeHours;
    }

    public void setDowntimeHours(double downtimeHours) {
        this.downtimeHours = downtimeHours;
    }

    public String getDowntimeReason() {
        return downtimeReason;
    }

    public void setDowntimeReason(String downtimeReason) {
        this.downtimeReason = downtimeReason;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }
}