package learn.mastery.data;

import learn.mastery.models.Host;

import java.util.ArrayList;

public interface HostRepository {
    public ArrayList<Host> findAll();
    public Host getHostById(String id) throws DataException;
}
