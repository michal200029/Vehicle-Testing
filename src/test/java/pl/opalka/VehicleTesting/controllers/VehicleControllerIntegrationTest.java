package pl.opalka.VehicleTesting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.opalka.VehicleTesting.entities.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class VehicleControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Flyway flyway;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldGetAllVehicles() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/all"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Vehicle[] vehicles = objectMapper.readValue(result.getResponse().getContentAsString(), Vehicle[].class);

        Assertions.assertEquals("Skoda",vehicles[0].getBrand());
        Assertions.assertEquals("Superb",vehicles[0].getModel());
        Assertions.assertEquals("grey",vehicles[0].getColor());
        Assertions.assertEquals(114,vehicles[0].getPower());

    }

    @Test
    void shouldReturnCorrectVehicle() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/3"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Vehicle vehicle = objectMapper.readValue(result.getResponse().getContentAsString(), Vehicle.class);

        Assertions.assertEquals("Audi",vehicle.getBrand());
        Assertions.assertEquals("A7",vehicle.getModel());
        Assertions.assertEquals("yellow",vehicle.getColor());
        Assertions.assertEquals(278,vehicle.getPower());
        Assertions.assertEquals(3L,vehicle.getId());


    }

    @Test
    void shouldReturn404WhenGet() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/10"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();

        String message =  result.getResolvedException().getMessage();

        Assertions.assertEquals("No vehicle has been found (id: 10 )",message);

    }

    @Test
    void shouldAddNewVehicle() throws Exception {

        Vehicle vehicle = new Vehicle();
            vehicle.setId(1L);
            vehicle.setColor("blue");
            vehicle.setBrand("brandX");
            vehicle.setModel("modelY");
            vehicle.setPower(355);


        MvcResult result = mockMvc.perform(
                 MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(vehicle)))
                    .andExpect(MockMvcResultMatchers.status().is(201))
                    .andReturn();
    }

    @AfterEach
    void cleanAll(){
        flyway.clean();
        flyway.migrate(); // Wyczyść baze i migracja na nowo

        /** po to aby jeden test nie zniszczył drugiego
         * np. Usuwasz cos z bazy w tesice X a w tescie Y chcesz sie odwolac
         * do elementu ktory zostal usuniety w X
         */
    }


}