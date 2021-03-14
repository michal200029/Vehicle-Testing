package pl.opalka.VehicleTesting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.opalka.VehicleTesting.entities.Vehicle;
import pl.opalka.VehicleTesting.exceptions.EntityNotFoundException;
import pl.opalka.VehicleTesting.exceptions.PowerIsNotCorrectException;
import pl.opalka.VehicleTesting.repositories.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    public List<Vehicle> findAllVehicle() {
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        return StreamSupport.stream(vehicles.spliterator(),true)
                .collect(Collectors.toList());
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        return vehicleOptional.orElseThrow(() -> new EntityNotFoundException("No vehicle has been found (id: " +id+" )"));
    }

    @Override
    public List<Vehicle> findAllVehiclesByColor(String color) {
        Iterable<Vehicle> vehicles = vehicleRepository.findAllByColor(color);
        return StreamSupport.stream(vehicles.spliterator(),true)
                .collect(Collectors.toList());
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        if(vehicle.getPower()>0 ) return vehicleRepository.save(vehicle);
        else throw new PowerIsNotCorrectException("Power must be greater then 0 (actual : "+vehicle.getPower()+")");
    }



}
