package app.flight.services;

import app.flight.NoFlightsExistExeption;
import app.flight.dao.FlightsDao;
import app.flight.dao.FlightsDaoMain;
import app.flight.model.FlightModel;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FlightServices {
    FlightsDao flightDao;

    public FlightServices(FlightsDao flightDao) {

        this.flightDao = flightDao;
    }

    public FlightServices() throws IOException {
        this.flightDao = new FlightsDaoMain();
    }

    public ArrayList<FlightModel> getAllFlightsPerDay() {
        return flightDao.getAllFlightsPerDay();
    }

    public FlightModel getFlightById(int id) throws IOException, NoFlightsExistExeption {
        return flightDao.getFlightById(id);
    }

    public ArrayList<FlightModel> searchedFlightsForReservation(String destination, String date, int ticketsCount) throws ParseException {
        return flightDao.searchedFlightsForReservation(destination, date, ticketsCount);
    }
}
