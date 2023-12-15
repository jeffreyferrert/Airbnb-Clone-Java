package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.models.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestRepository repository = new GuestRepositoryDouble();
    GuestService service = new GuestService(repository);

    @Test
    void shouldFindAll() throws DataException {
        assertEquals(2, service.findAll().size());
    }

    @Test
    void shouldGetGuestById() throws DataException {
        Guest actual = service.getGuestById(1);
        assertNotNull(actual);
        assertEquals(actual.getFirstName(), "Sullivan");
        assertEquals(actual.getLastName(), "Lomas");
        assertEquals(actual.getEmail(), "slomas0@mediafire.com");
        assertEquals(actual.getPhone(), "(702) 7768761");
        assertEquals(actual.getState(), "NV");

        actual = service.getGuestById(1001);
        assertNull(actual);
    }

    @Test
    void shouldGetGuestByEmail() throws DataException {
        Guest actual = service.getGuestByEmail("slomas0@mediafire.com");
        assertNotNull(actual);
        assertEquals(1, actual.getId());
        assertEquals("Sullivan", actual.getFirstName());
        assertEquals("Lomas", actual.getLastName());

        actual = service.getGuestByEmail("");
        assertNull(actual);

        actual = service.getGuestByEmail("test@gmail.com");
        assertNull(actual);
    }
}