package com.drivego.maintenance.config;

import com.drivego.maintenance.model.MaintenanceRecord;
import com.drivego.maintenance.service.MaintenanceRecordService;
import com.drivego.maintenance.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(MaintenanceRecordService maintenanceService) {
        return args -> {
            // Data is now seeded via SQL scripts in data.sql
            // This method is kept for any additional programmatic seeding if needed
            System.out.println("Application started with SQL script-based data seeding");
        };
    }
}



