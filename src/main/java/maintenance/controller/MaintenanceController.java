package com.drivego.maintenance.controller;

import com.drivego.maintenance.model.MaintenanceRecord;
import com.drivego.maintenance.service.MaintenanceRecordService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceRecordService service;

    public MaintenanceController(MaintenanceRecordService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public String list(@RequestParam(value = "q", required = false) String query, Model model) {
        model.addAttribute("records", service.searchByVehicleId(query));
        model.addAttribute("q", query == null ? "" : query);
        return "maintenance/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("record", new MaintenanceRecord());
        return "maintenance/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@Valid @ModelAttribute("record") MaintenanceRecord record,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "maintenance/form";
        }
        try {
            service.save(record);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Maintenance record for vehicle " + record.getVehicleId() + " has been created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to create maintenance record: " + e.getMessage());
        }
        return "redirect:/maintenance";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        MaintenanceRecord record = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        model.addAttribute("record", record);
        return "maintenance/view";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        MaintenanceRecord record = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        model.addAttribute("record", record);
        return "maintenance/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("record") MaintenanceRecord record,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "maintenance/form";
        }
        try {
            record.setId(id);
            service.save(record);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Maintenance record for vehicle " + record.getVehicleId() + " has been updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to update maintenance record: " + e.getMessage());
        }
        return "redirect:/maintenance";
    }

    @GetMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public String toggleStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            MaintenanceRecord record = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance record not found with id: " + id));
            
            String oldStatus = record.isAvailable() ? "Available" : "Under Maintenance";
            service.toggleStatus(id);
            
            // Get the updated record to show new status
            MaintenanceRecord updatedRecord = service.findById(id).orElse(record);
            String newStatus = updatedRecord.isAvailable() ? "Available" : "Under Maintenance";
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Vehicle " + record.getVehicleId() + " status changed from '" + oldStatus + "' to '" + newStatus + "' successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to toggle maintenance status: " + e.getMessage());
        }
        return "redirect:/maintenance";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Check if record exists before deleting
            MaintenanceRecord record = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance record not found with id: " + id));
            
            String vehicleId = record.getVehicleId();
            service.deleteById(id);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Maintenance record for vehicle " + vehicleId + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to delete maintenance record: " + e.getMessage());
        }
        return "redirect:/maintenance";
    }
}


