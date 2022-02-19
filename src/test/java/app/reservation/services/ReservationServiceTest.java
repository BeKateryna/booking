package app.reservation.services;

import app.reservation.dao.ReservationDao;
import app.reservation.model.ReservationModel;
import app.user.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ReservationServiceTest {
    private ReservationService service;
    private ReservationDao mockDao;

    @BeforeEach
    public void init() throws IOException {
        mockDao = mock(ReservationDao.class);
        service = new ReservationService(mockDao);
        ReservationModel testModel = new ReservationModel();
        testModel.setId("12");
        testModel.setFlightId(5);
        testModel.setPassengers(Arrays.asList("1", "2", "5"));
        when(mockDao.findReservationByFlightId(12))
                .thenReturn(testModel);
        when(mockDao.addNewReservation(any(ReservationModel.class)))
                .thenReturn(testModel);
        when(mockDao.create(any(ReservationModel.class)))
                .thenReturn(testModel);
        when(mockDao.countReservations(testModel.getFlightId()))
                .thenReturn(testModel.getPassengers().size());
    }

    @Test
    public void should_calladdNewReservation_and_update_reservations() throws IOException {
        long flightId = 12;
        service.reserve(new ArrayList<>(), flightId);
        verify(mockDao, atLeast(1)).addNewReservation(any());
    }

    @Test
    public void should_create_newReservations__if_its_not_exist() throws IOException {
        long flightId = 100;
        service.reserve(new ArrayList<>(), flightId);
        verify(mockDao, atLeast(1)).create(any());
    }

    @Test
    public void should_return_users_reservations_if_they_are_present() throws IOException {
        UserModel user = new UserModel();
        user.setId("88");
        user.setFirstName("Kate");
        user.setLastName("Biehantseva");
        HashMap<String, Long> searchResult = new HashMap<>();
        searchResult.put("8", 1L);
        when(mockDao.getUserReserves(user)).thenReturn(searchResult);
        Map<String, Long> expect = new HashMap<>();
        expect.put("8", 1L);
        Map<String, Long> result = mockDao.getUserReserves(user);
        Assertions.assertEquals(expect, result);
    }
}