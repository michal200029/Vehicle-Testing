package pl.opalka.VehicleTesting.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.opalka.VehicleTesting.entities.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository  extends CrudRepository<Vehicle,Long> {

    List<Vehicle> findAllByColor(String color);
    Optional<Vehicle> findById(Long id);

}
