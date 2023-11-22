package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;

public class HostService {
    private HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host getHostById(String id) throws DataException {
        return repository.getHostById(id);
    }
}
