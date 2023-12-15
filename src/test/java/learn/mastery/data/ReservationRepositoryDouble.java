package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import java.util.ArrayList;
import learn.mastery.models.Guest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    List<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        return reservations.stream()
                .filter(reservation -> reservation.getHost().equals(host))
                .collect(Collectors.toList());
    }


    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setId(reservations.size()+1);
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, reservation);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        return reservations.removeIf(r -> r.getId() == reservation.getId());
    }
}
