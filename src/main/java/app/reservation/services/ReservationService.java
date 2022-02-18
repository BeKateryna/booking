package app.reservation.services;
import app.reservation.dao.ReservationDao;
import app.reservation.model.ReservationModel;
import app.user.model.UserModel;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService() throws IOException {reservationDao = new ReservationDao();}

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationModel reserve (List<UserModel> users, long flightId) {
        List<String> passengers = users.stream()
                .map(UserModel::getId)
                .collect(Collectors.toList());
        try {
            ReservationModel existReservation = reservationDao.findReservationByFlightId(flightId);
            if (existReservation != null) {
                existReservation.getPassengers().addAll(passengers);
                return reservationDao.addNewReservation(existReservation);
//                return reservationDao.addNewReservation(existReservation);
            } else {
                ReservationModel newReservation = new ReservationModel();
                String id = UUID.randomUUID().toString();
                newReservation.setFlightId(flightId);
                newReservation.setPassengers(passengers);
                newReservation.setId(id);
                return reservationDao.create(newReservation);
            }
        } catch (IOException ioException) {
            System.out.println("something went wrong...");
            return null;
        }
    }

    public int countReservations(long flightId) throws IOException {
        return reservationDao.countReservations(flightId);
    }

    public void delete(String id) throws IOException {
        reservationDao.delete(id);
    }

    public Map<String, Long> getUserReserves(UserModel user) throws IOException {
        return reservationDao.getUserReserves(user);
    }
}
