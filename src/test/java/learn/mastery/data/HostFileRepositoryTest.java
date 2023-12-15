package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String PATH = "./data/hosts.csv";

    HostFileRepository repository = new HostFileRepository(PATH);

    @Test
    void findAll() {
        assertEquals(1000, repository.findAll().size());
    }

    @Test
    void getHostById() {
        Host actual = repository.getHostById("eyearnes0@sfgate.com");
        assertEquals(actual.getId(),"3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertEquals(actual.getLastName(),"Yearnes");
        assertEquals(actual.getCity(),"Amarillo");

        actual = repository.getHostById("nes0@sfg");
        assertNull(actual);
    }
}