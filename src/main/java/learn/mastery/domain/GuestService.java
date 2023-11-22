package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;

public class GuestService {
    private GuestRepository guestRepository;

    public GuestService(GuestRepository repository) {
        this.guestRepository = repository;
    }

    public Guest getGuestById(int id) throws DataException {
        return guestRepository.getGuestById(id);
    }

    public Guest getGuestByEmail(String guestEmail) throws DataException {
        return guestRepository.getGuestByEmail(guestEmail);
    }
}
