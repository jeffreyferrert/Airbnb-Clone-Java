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
    static final String PATH = "./data/reservations_test";
    static final String PATH_TEST = "./data/reservations_test2";

    ReservationFileRepository repository = new ReservationFileRepository(PATH_TEST);
    private GuestRepository guestRepository = new GuestFileRepository("./data/hosts.csv");

    @BeforeEach
    public void setUp() throws IOException {
        Path seedPath = Paths.get(PATH);
        Path testPath = Paths.get(PATH_TEST);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
        repository.setGuestRepository(guestRepository);
    }


    @Test
    void shouldFindByHost() throws DataException {
        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        host.setLastName("Yearnes");
        host.setEmail("eyearnes0@sfgate.com");
        host.setPhone("(806) 1783815");
        host.setAddress("3 Nova Trail");
        host.setCity("Amarillo");
        host.setState("TX");
        host.setZip(79182);
        host.setStandardRate(BigDecimal.valueOf(340));
        host.setWeekendRate(BigDecimal.valueOf(425));

        List<Reservation> all = repository.findByHost(host);
        System.out.println(all);
        System.out.println(all.get(0));
        assertEquals(870, all.get(0).getTotal());
    }

    @Test
    void shouldAdd() throws DataException {
        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        host.setLastName("Yearnes");
        host.setEmail("eyearnes0@sfgate.com");
        host.setPhone("(806) 1783815");
        host.setAddress("3 Nova Trail");
        host.setCity("Amarillo");
        host.setState("TX");
        host.setZip(79182);
        host.setStandardRate(BigDecimal.valueOf(340));
        host.setWeekendRate(BigDecimal.valueOf(425));

        Guest guest = new Guest();
        guest.setId(0);
        guest.setFirstName("Sullivan");
        guest.setLastName("Lomas");
        guest.setEmail("slomas0@mediafire.com");
        guest.setPhone("(702) 7768761");
        guest.setState("NV");

        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStart( LocalDate.of(2023,12,10));
        reservation.setEnd( LocalDate.of(2023,12,30));
        reservation.setTotal(BigDecimal.valueOf(1000));

        reservation = repository.add(reservation);
        assertEquals(2, reservation.getId());
    }

    @Test
    void shouldUpdate() {

    }

    @Test
    void shouldDelete() {
//        boolean result = repository.delete();
//        assertTrue(result);
    }
}