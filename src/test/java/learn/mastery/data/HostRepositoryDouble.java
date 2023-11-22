package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        host.setLastName("Yearnes");
        host.setEmail("eyearnes0@sfgate.com");
        host.setPhone("(806) 1783815");
        host.setAddress("3 Nova Trail");
        host.setCity("Amarillo");
        host.setState("TX");
        host.setZip(79182);
        host.setStandardRate(BigDecimal.valueOf(340));
        host.setWeekendRate(BigDecimal.valueOf(425));
        hosts.add(host);
    }

    @Override
    public Host getHostById(String hostId) throws DataException {
        return hosts.stream().filter(i -> i.getId().equalsIgnoreCase(hostId)).findFirst().orElse(null);
    }
}
