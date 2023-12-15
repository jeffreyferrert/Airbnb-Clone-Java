package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;

import java.util.ArrayList;

public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository repository) {
        this.guestRepository = repository;
    }

    public ArrayList<Guest> findAll() {
        return guestRepository.findAll();
    }

    public Guest getGuestById(int id) throws DataException {
        return guestRepository.getGuestById(id);
    }

    public Guest getGuestByEmail(String guestEmail) throws DataException {
        return guestRepository.getGuestByEmail(guestEmail);
    }
}
