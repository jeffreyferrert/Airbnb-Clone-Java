package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Jeff", "Yearnes", "eyearnes0@sfgate.com", "(806) 1783815", "3 Nova Trail", "Amarillo", "TX", 79182, BigDecimal.valueOf(340), BigDecimal.valueOf(425));
    Guest guest = new Guest(1, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", "NV");
    Reservation reservation1 = new Reservation(0, host, guest, LocalDate.of(2023, 12, 10), LocalDate.of(2023, 12, 30), BigDecimal.valueOf(500));
    Reservation reservation2 = new Reservation(1, host, guest, LocalDate.of(2023, 12, 15), LocalDate.of(2023, 12, 20), BigDecimal.valueOf(700));
    List<Reservation> reservations = new ArrayList<>(Arrays.asList(reservation1, reservation2));

    ReservationRepository reservationRepository = new ReservationRepositoryDouble(reservations);

    ReservationService service = new ReservationService(reservationRepository);

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2023, 12, 7));
        reservation.setEnd(LocalDate.of(2023, 12, 9));
        reservation.setTotal(BigDecimal.valueOf(600));

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());

        List<Reservation> reservations = service.findByHost(host);
        assertEquals(3, reservations.size());
    }

    @Test
    void findByHost() throws DataException {
        List<Reservation> reservations = service.findByHost(host);
        assertEquals(2, reservations.size());
        assertEquals(BigDecimal.valueOf(500), reservations.get(0).getTotal());
        assertEquals(BigDecimal.valueOf(700), reservations.get(1).getTotal());
    }

    @Test
    void update() throws DataException {
        List<Reservation> reservations = service.findByHost(host);
        Reservation reservation = reservations.get(0);
        reservation.setStart(LocalDate.of(2024, 12, 16));
        reservation.setEnd(LocalDate.of(2024, 12, 22));

        Result<Reservation> result = service.update(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void delete() throws DataException {
        List<Reservation> reservations = service.findByHost(host);
        Reservation reservation = reservations.get(0);
        assertTrue(service.delete(reservation).isSuccess());
    }
}