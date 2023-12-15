package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;

import java.util.ArrayList;

public class HostService {
    private HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Host> findAll() {
        return repository.findAll();
    }

    public Host getHostById(String id) throws DataException {
        return repository.getHostById(id);
    }
}
