package app.flight.services;

import app.flight.NoFlightsExistExeption;
import app.flight.dao.FlightsDaoMain;
import app.flight.model.FlightModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import static org.mockito.Mockito.*;
class FlightServicesTest {
    private FlightServices flightServicesTest;
    FlightModel flightModel;
    @BeforeEach
    public void init() throws ParseException, NoFlightsExistExeption {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2022-03-01");
        FlightsDaoMain flight = mock(FlightsDaoMain.class);
        flightServicesTest = new FlightServices(flight);
        flightModel = new FlightModel();
        flightModel.setId(1);
        flightModel.setDestination("London");
        flightModel.setSeats(20);
        flightModel.setDate(date);

        when(flight.getFlightById(1))
                .thenReturn(flightModel);
        when(flight.getAllFlightsPerDay())
                .thenReturn(new ArrayList<>(Collections.singleton(flightModel)));
        when(flight.searchedFlightsForReservation("London", "2022-03-01", 2))
                .thenReturn(new ArrayList<>(Collections.singleton(flightModel)));
    }

    @Test
    public void should_return_Flight_when_exist() throws IOException, NoFlightsExistExeption {
        int searchedId = 1;
        when(flightServicesTest.getFlightById(searchedId)).thenReturn(flightModel);
    }
}