package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    HostRepository repository = new HostRepositoryDouble();
    HostService service = new HostService(repository);

    @Test
    void getHostById() throws DataException {
        Host actual = service.getHostById("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(actual);
        assertEquals("Yearnes", actual.getLastName());
        assertEquals("eyearnes0@sfgate.com", actual.getEmail());
        assertEquals("(806) 1783815", actual.getPhone());
        assertEquals("3 Nova Trail", actual.getAddress());
        assertEquals("Amarillo", actual.getCity());
        assertEquals("TX", actual.getState());
        assertEquals(79182, actual.getZip());
        assertEquals(BigDecimal.valueOf(340), actual.getStandardRate());
        assertEquals(BigDecimal.valueOf(425), actual.getWeekendRate());


        actual = service.getHostById("dddddddd-ad45-49a8-8962-d50b53f84b15");
        assertNull(actual);
    }
}