package pl.opalka.VehicleTesting.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.opalka.VehicleTesting.entities.Vehicle;
import pl.opalka.VehicleTesting.exceptions.PowerIsNotCorrectException;
import pl.opalka.VehicleTesting.repositories.VehicleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
class VehicleServiceImplTest {

    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository;


    @BeforeEach
    void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleService = new VehicleServiceImpl(vehicleRepository);
    }

    @Test
    @DisplayName("Should Return Selected Color")
    void shouldReturnSelectedColor() {
        //given
        List<Vehicle> all = setUpData();
        when(vehicleRepository.findAllByColor(any())).thenReturn(all);

        //when
        List<Vehicle> actualData = vehicleService.findAllVehiclesByColor("yellow");

        //then
        Assertions.assertEquals("yellow",actualData.get(0).getColor());
        Assertions.assertEquals("brand1",actualData.get(0).getBrand());
        Assertions.assertEquals("model1",actualData.get(0).getModel());
        Assertions.assertEquals(151,actualData.get(0).getPower());
        Assertions.assertEquals(1L,actualData.get(0).getId());

    }
    @Test
    @DisplayName("Should Return Selected Vehicle")
    void shouldReturnSelectedVehicle() {
        //given
        Vehicle vehicle = new Vehicle();
            vehicle.setId(5L);
            vehicle.setColor("red");
            vehicle.setBrand("brandX");
            vehicle.setModel("modelY");
            vehicle.setPower(459);

        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));

        //when
        Vehicle vehicleFound = vehicleService.findVehicleById(5L);

        //then
        Assertions.assertEquals("red",vehicleFound.getColor());
        Assertions.assertEquals("brandX",vehicleFound.getBrand());
        Assertions.assertEquals("modelY",vehicleFound.getModel());
        Assertions.assertEquals(459,vehicleFound.getPower());
        Assertions.assertEquals(5L,vehicleFound.getId());

    }

    @Test
    @DisplayName("Should Return Correct Amount")
    void shouldReturnCorrectAmount() {
        //given
        List<Vehicle> all = setUpData();
        when(vehicleRepository.findAll()).thenReturn(all);

        //when
        List<Vehicle> vehiclesFound = vehicleService.findAllVehicle();

        //then
        Assertions.assertEquals(2,vehiclesFound.size());
    }

    @ParameterizedTest
    @DisplayName("Should Throw Power Exception ")
    @ValueSource(strings = {"-58","0","-1"})
    void shouldThrowPowerException(String power){
        //given
        Vehicle vehicle = new Vehicle();
            vehicle.setId(1L);
            vehicle.setColor("blue");
            vehicle.setBrand("brandX");
            vehicle.setModel("modelY");
            vehicle.setPower(Integer.parseInt(power));

        when(vehicleRepository.save(any())).thenReturn(PowerIsNotCorrectException.class);

        //then
       Assertions.assertThrows(PowerIsNotCorrectException.class,() ->{
           vehicleService.saveVehicle(vehicle);
        });
    }


    List<Vehicle> setUpData(){
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setColor("yellow");
        vehicle1.setBrand("brand1");
        vehicle1.setModel("model1");
        vehicle1.setPower(151);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setColor("red");
        vehicle2.setBrand("brand2");
        vehicle2.setModel("model2");
        vehicle2.setPower(98);

        List<Vehicle> vehicles = Arrays.asList(vehicle1,vehicle2);

        return vehicles;
    }
}