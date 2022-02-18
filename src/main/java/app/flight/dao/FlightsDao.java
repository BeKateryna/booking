package app.flight.dao;
import app.flight.NoFlightsExistExeption;
import app.flight.model.FlightModel;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface FlightsDao {
    FlightModel getFlightById(int id) throws IOException, NoFlightsExistExeption;
    ArrayList<FlightModel> getAllFlightsPerDay();
    ArrayList<FlightModel> searchedFlightsForReservation(String destination, String date, int ticketsCount) throws ParseException;
}
