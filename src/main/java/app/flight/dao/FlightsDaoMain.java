package app.flight.dao;

import app.flight.NoFlightsExistExeption;
import app.flight.model.FlightModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FlightsDaoMain implements FlightsDao {
    private final List<FlightModel> flightDataBase;

    public FlightsDaoMain() throws IOException {
//        InputStream database = this.getClass().getClassLoader().getResourceAsStream("flightDataBase.json");
//        ObjectMapper mapper = new ObjectMapper();
//        flightDataBase = mapper.readValue(database, new TypeReference<List<FlightModel>>() {
//        });
        File database = new File("src/main/resources/flightDataBase.json");
        ObjectMapper mapper = new ObjectMapper();
        flightDataBase = mapper.readValue(database, new TypeReference<List<FlightModel>>() {
        });
    }

    @Override
    public FlightModel getFlightById(int id) throws NoFlightsExistExeption {
        return flightDataBase.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoFlightsExistExeption(id));
    }

    @Override
    public ArrayList<FlightModel> getAllFlightsPerDay() {
        Date startDate = java.sql.Date.valueOf(LocalDateTime.now().toLocalDate());
        Date endDate = java.sql.Date.valueOf(LocalDateTime.now().plusHours(24).toLocalDate());
        return flightDataBase.stream()
                .filter(e -> e.getDate().after(startDate) && e.getDate().before(endDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<FlightModel> searchedFlightsForReservation(String destination, String date, int ticketsCount) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date userDate = sdf.parse(date);

        return flightDataBase.stream()
                .filter(e -> e.getDestination().toLowerCase().equals(destination.toLowerCase()))
                .filter(e -> e.getSeats() > ticketsCount)
                .filter(e -> {
                    long diff = e.getDate().getTime() - userDate.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    return days == 0;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
