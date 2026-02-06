package com.drivego.maintenance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarRentalController {

    @GetMapping("/car-rental")
    public String carRental() {
        return "car/index";
    }
}






