package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import java.util.List;

public interface ReservationRepository {
    public List<Reservation> findByHost(Host host) throws DataException;

    public Reservation add(Reservation reservation) throws DataException;

    public boolean update(Reservation reservation) throws DataException;

    public boolean delete(Reservation reservation) throws DataException;
}
