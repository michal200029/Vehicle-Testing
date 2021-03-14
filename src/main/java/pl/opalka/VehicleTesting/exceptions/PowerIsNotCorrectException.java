package pl.opalka.VehicleTesting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PowerIsNotCorrectException extends RuntimeException{

    public PowerIsNotCorrectException(String message) {
        super(message);
    }
}
