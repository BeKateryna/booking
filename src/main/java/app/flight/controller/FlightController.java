package app.flight.controller;
import app.flight.NoFlightsExistExeption;
import app.flight.model.FlightModel;
import app.flight.services.FlightServices;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FlightController {
    FlightServices flightServices;

    public FlightController(FlightServices flightServices) {
        this.flightServices = flightServices;
    }

    public FlightController() throws IOException {
        flightServices = new FlightServices();
    }

    public ArrayList<FlightModel> getAllFlightsPerDay() {
        return flightServices.getAllFlightsPerDay();
    }

    public FlightModel getFlightById(int id) throws IOException, NoFlightsExistExeption {
        return flightServices.getFlightById(id);
    }

    public ArrayList<FlightModel> searchedFlightsForReservation(String destination, String date, int ticketsCount) throws ParseException {
        return flightServices.searchedFlightsForReservation(destination, date, ticketsCount);
    }
}
