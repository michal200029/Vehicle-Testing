package pl.opalka.VehicleTesting.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.opalka.VehicleTesting.entities.Vehicle;
import pl.opalka.VehicleTesting.services.VehicleService;

import java.util.List;


@RestController
public class VehicleController {

    @Autowired
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.findAllVehicle();
    }

    @GetMapping("/color")
    public List<Vehicle> getAllVehiclesByColor(@RequestParam String color){
        return vehicleService.findAllVehiclesByColor(color);
    }

    @GetMapping("/{id}")
    public Vehicle getVehiclesById(@PathVariable Long id){
        return vehicleService.findVehicleById(id);
    }

    @PostMapping
    public ResponseEntity<?> saveVehicle(@RequestBody Vehicle vehicle){
        vehicleService.saveVehicle(vehicle);
        return ResponseEntity.status(201).build();
    }




}
