package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";
    private GuestRepository guestRepository = new GuestFileRepository("./data/guests.csv");

    Host host = new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Jeff", "Yearnes", "eyearnes0@sfgate.com", "(806) 1783815", "3 Nova Trail", "Amarillo", "TX", 79182, BigDecimal.valueOf(340), BigDecimal.valueOf(425));
    Guest guest = new Guest(1, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", "NV");

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    public void setUp() throws IOException {
        repository.setGuestRepository(guestRepository);

        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHost() throws DataException {
        List<Reservation> all = repository.findByHost(host);
        assertEquals(1, all.get(0).getId());
        assertEquals(BigDecimal.valueOf(500), all.get(0).getTotal());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2023, 12, 10));
        reservation.setEnd(LocalDate.of(2023, 12, 30));
        reservation.setTotal(BigDecimal.valueOf(500));
        reservation = repository.add(reservation);

        List<Reservation> all = repository.findByHost(host);
        assertEquals(2, all.size());
        assertEquals(2, reservation.getId());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart(LocalDate.of(2023, 12, 10));
        reservation.setEnd(LocalDate.of(2023, 12, 30));
        reservation.setTotal(BigDecimal.valueOf(500));
        reservation = repository.add(reservation);

        List<Reservation> all = repository.findByHost(host);
        reservation = all.get(0);
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStart(LocalDate.of(2023, 12, 15));
        reservation.setEnd(LocalDate.of(2023, 12, 20));

        boolean result = repository.update(reservation);
        assertTrue(result);
    }

    @Test
    void shouldDelete() throws DataException {
        List<Reservation> all = repository.findByHost(host);
        Reservation reservation = all.get(0);
        boolean result = repository.delete(reservation);
        assertTrue(result);
    }
}