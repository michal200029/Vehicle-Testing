package pl.opalka.VehicleTesting.services;

import pl.opalka.VehicleTesting.entities.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAllVehicle();
    Vehicle findVehicleById(Long id);
    List<Vehicle> findAllVehiclesByColor(String color);
    Vehicle saveVehicle(Vehicle vehicle);


}
