package com.drivego.maintenance.service;

import com.drivego.maintenance.model.MaintenanceRecord;
import com.drivego.maintenance.repository.MaintenanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository repository;

    public MaintenanceRecordService(MaintenanceRecordRepository repository) {
        this.repository = repository;
    }

    public List<MaintenanceRecord> findAll() {
        return repository.findAll();
    }

    public List<MaintenanceRecord> searchByVehicleId(String vehicleId) {
        if (vehicleId == null || vehicleId.isBlank()) {
            return findAll();
        }
        return repository.findByVehicleIdContainingIgnoreCase(vehicleId);
    }

    public Optional<MaintenanceRecord> findById(Long id) {
        return repository.findById(id);
    }

    public MaintenanceRecord save(MaintenanceRecord record) {
        // Generate ID for new records
        if (record.getId() == null) {
            Long nextId = getNextId();
            record.setId(nextId);
        }
        return repository.save(record);
    }
    
    private Long getNextId() {
        // Simple counter starting from 1
        // In a real application, you might want to use a sequence table or atomic counter
        List<MaintenanceRecord> allRecords = repository.findAll();
        if (allRecords.isEmpty()) {
            return 1L;
        }
        // Find the highest ID and add 1
        Long maxId = allRecords.stream()
                .mapToLong(MaintenanceRecord::getId)
                .max()
                .orElse(0L);
        return maxId + 1;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void toggleStatus(Long id) {
        MaintenanceRecord record = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance record not found with id: " + id));
        
        // Simply toggle the underMaintenance status - admin has full control
        // Handle null case to prevent NullPointerException
        Boolean currentStatus = record.getUnderMaintenance();
        if (currentStatus == null) {
            currentStatus = Boolean.FALSE;
        }
        record.setUnderMaintenance(!currentStatus);
        
        repository.save(record);
    }
    
    /**
     * Updates maintenance status for all records based on their estimated readiness dates.
     * This method can be called periodically to ensure status accuracy.
     */
    public void updateAllMaintenanceStatuses() {
        List<MaintenanceRecord> allRecords = repository.findAll();
        for (MaintenanceRecord record : allRecords) {
            // Trigger the automatic status update logic
            record.setEstimatedReadiness(record.getEstimatedReadiness());
        }
        repository.saveAll(allRecords);
    }
}



