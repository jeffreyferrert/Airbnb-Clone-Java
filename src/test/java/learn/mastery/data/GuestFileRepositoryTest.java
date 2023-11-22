package learn.mastery.data;

import learn.mastery.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String PATH = "./data/guests.csv";

    GuestFileRepository repository = new GuestFileRepository(PATH);

    @Test
    void shouldGetGuestById() {
        Guest actual = repository.getGuestById(1);
        assertEquals(actual.getEmail(),"slomas0@mediafire.com");
        assertEquals(actual.getFirstName(),"Sullivan");
        assertEquals(actual.getLastName(),"Lomas");

        actual = repository.getGuestById(1001);
        assertNull(actual);
    }

    @Test
    void shouldGetGuestByEmail() {
        Guest actual = repository.getGuestByEmail("slomas0@mediafire.com");
        assertEquals(actual.getId(),1);
        assertEquals(actual.getFirstName(),"Sullivan");
        assertEquals(actual.getLastName(),"Lomas");

        actual = repository.getGuestByEmail("");
        assertNull(actual);

        actual = repository.getGuestByEmail("test@gmail.com");
        assertNull(actual);
    }
}