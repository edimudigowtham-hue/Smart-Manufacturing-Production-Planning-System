package com.genc.smpps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class MaintenanceWorkOrder {

    public interface OnAssign {}
    public interface OnSpare {}
    public interface OnClose {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(groups = {OnAssign.class, OnSpare.class, OnClose.class}, message = "Work order ID must be greater than 0")
    private int workOrderId;

    @Positive(message = "Machine ID must be greater than 0")
    private int machineId;

    @NotBlank(message = "Maintenance type is required")
    @Pattern(regexp = "^(PREVENTIVE|BREAKDOWN|CALIBRATION)$", message = "Maintenance type must be PREVENTIVE, BREAKDOWN, or CALIBRATION")
    private String maintenanceType;

    @NotNull(message = "Scheduled date is required")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date scheduledDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @Pattern(regexp = "^(OPEN|IN_PROGRESS|COMPLETED)$", message = "Work order status must be OPEN, IN_PROGRESS, or COMPLETED")
    private String workOrderStatus;

    //additional fields for tracking maintenance details

    @NotBlank(groups = OnAssign.class, message = "Technician name is required")
    @Size(groups = OnAssign.class, max = 100, message = "Technician name must not exceed 100 characters")
    private String technician;

    @NotBlank(groups = OnSpare.class, message = "Spare parts details are required")
    @Size(groups = OnSpare.class, max = 255, message = "Spare parts description must not exceed 255 characters")
    private String spareParts;

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    @AssertTrue(message = "Completion date must be after scheduled date")
    public boolean isCompletionDateAfterScheduledDate() {
        if (scheduledDate == null || completionDate == null) {
            return true;
        }
        return completionDate.after(scheduledDate);
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(String spareParts) {
        this.spareParts = spareParts;
    }
}
