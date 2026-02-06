package com.drivego.maintenance.model;

import com.drivego.maintenance.util.DateAfter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_records")
@DateAfter
public class MaintenanceRecord {

    @Id
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String vehicleId;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String maintenanceType;

    @NotNull
    private LocalDate serviceDate;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String serviceCenter;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    @Column(precision = 12, scale = 2)
    private BigDecimal cost;

    @Size(max = 500)
    @Column(length = 500)
    private String notes;

    @Column(name = "under_maintenance", nullable = false)
    private Boolean underMaintenance = Boolean.FALSE;

    @Size(max = 100)
    @Column(length = 100)
    private String technician;

    @Column
    private LocalDate estimatedReadiness;

    @PrePersist
    public void applyDefaults() {
        if (underMaintenance == null) {
            underMaintenance = Boolean.FALSE;
        }
        

        updateMaintenanceStatus();
    }
    
    @PreUpdate
    public void updateMaintenanceStatus() {
        // Only auto-update if underMaintenance is null (new records)
        // This prevents automatic status updates from overriding manual admin toggles
        if (underMaintenance == null) {
            if (estimatedReadiness != null) {
                underMaintenance = estimatedReadiness.isAfter(LocalDate.now());
            } else {
                underMaintenance = Boolean.FALSE;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getUnderMaintenance() {
        return underMaintenance;
    }

    public void setUnderMaintenance(Boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public LocalDate getEstimatedReadiness() {
        return estimatedReadiness;
    }

    public void setEstimatedReadiness(LocalDate estimatedReadiness) {
        this.estimatedReadiness = estimatedReadiness;
        updateMaintenanceStatus();
    }


    public boolean isAvailable() {
        return underMaintenance == null || !underMaintenance;
    }
    

    public String getStatusText() {
        return isAvailable() ? "Available" : "Under Maintenance";
    }
    

    public String getStatusBadgeClass() {
        return isAvailable() ? "bg-success" : "bg-warning";
    }
}



