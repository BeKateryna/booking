package app.reservation.dao;

import app.reservation.model.ReservationModel;
import app.user.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationDao {
    private File reservationDataBase;

    public ReservationDao() throws IOException {
        reservationDataBase = new File("src/main/resources/reservationDataBase.json");
        reservationDataBase.createNewFile();
//        reservationDataBase = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("reservationDataBase.json")).toString());
    }

    public ReservationModel create(ReservationModel reservationModel) throws IOException {
        List<ReservationModel> reservationDataBaseList = findAll();
        reservationDataBaseList.add(reservationModel);
        updateReservationDataBase(reservationDataBaseList);
        return reservationModel;
    }

    public ReservationModel addNewReservation(ReservationModel newReservationModel) throws IOException {
        List<ReservationModel> newReservationList = findAll().stream()
                .map(rm -> {
                    if (rm.getId().equals(newReservationModel.getId())) {
                        return newReservationModel;
                    } else {
                        return rm;
                    }
                })
                .collect(Collectors.toList());
        updateReservationDataBase(newReservationList);
        return newReservationModel;
    }

    public void delete(String id) throws IOException {
        List<ReservationModel> deleteReservation = findAll().stream()
                .filter(changedReservations -> !changedReservations.getId().equals(id))
                .collect(Collectors.toList());
        updateReservationDataBase(deleteReservation);
    }


    public ReservationModel findReservationByFlightId(long flightId) throws IOException {
        return findAll().stream()
                .filter(rm -> rm.getFlightId() == flightId)
                .findFirst()
                .orElse(null);
    }

    public int countReservations(long flightId) throws IOException {
        return findAll().stream()
                .filter(rm -> rm.getFlightId() == flightId)
                .findFirst()
                .map(r -> r.getPassengers().size())
                .orElse(0);
    }

    public Map<String, Long> getUserReserves(UserModel user) throws IOException {
        return findAll().stream()
                .filter(reservationModel -> reservationModel.getPassengers().contains(user.getId()))
                .collect(Collectors.toMap(ReservationModel::getId, ReservationModel::getFlightId));
    }

    public List<ReservationModel> findAll() throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(reservationDataBase, new TypeReference<List<ReservationModel>>() {});
    }

    private void updateReservationDataBase(List<ReservationModel> reservationModelList) throws IOException {
        try (OutputStream fileOutputStream = new FileOutputStream(reservationDataBase)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String newDataBase = objectMapper.writeValueAsString(reservationModelList);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.write(newDataBase);
            writer.flush();
        }
    }
}
