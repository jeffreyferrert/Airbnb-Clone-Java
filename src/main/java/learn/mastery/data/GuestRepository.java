package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.ArrayList;

public interface GuestRepository {
    public ArrayList<Guest> findAll();

    public Guest getGuestById(int id) throws DataException;

    public Guest getGuestByEmail(String guestEmail) throws DataException;
}
