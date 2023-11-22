package learn.mastery.data;

import learn.mastery.models.Guest;

public interface GuestRepository {
    public Guest getGuestById(int id) throws DataException;

    public Guest getGuestByEmail(String guestEmail) throws DataException;
}
