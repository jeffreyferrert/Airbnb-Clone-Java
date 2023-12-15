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

        host = new Host();
        host.setId("a0d911e7-4fde-4e4a-bdb7-f047f15615e8");
        host.setLastName("Rhodes");
        host.setEmail("krhodes1@posterous.com");
        host.setPhone("(478) 7475991");
        host.setAddress("7262 Morning Avenue");
        host.setCity("Macon");
        host.setState("GA");
        host.setZip(31296);
        host.setStandardRate(BigDecimal.valueOf(295));
        host.setWeekendRate(BigDecimal.valueOf(368));
        hosts.add(host);
    }

    @Override
    public ArrayList<Host> findAll() {
        return hosts;
    }

    @Override
    public Host getHostById(String hostId) throws DataException {
        return hosts.stream().filter(i -> i.getId().equalsIgnoreCase(hostId)).findFirst().orElse(null);
    }
}
