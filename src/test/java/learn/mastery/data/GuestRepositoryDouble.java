package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        Guest guest = new Guest();
        guest.setId(1);
        guest.setFirstName("Sullivan");
        guest.setLastName("Lomas");
        guest.setEmail("slomas0@mediafire.com");
        guest.setPhone("(702) 7768761");
        guest.setState("NV");
        guests.add(guest);

        guest = new Guest();
        guest.setId(2);
        guest.setFirstName("Olympie");
        guest.setLastName("Gecks");
        guest.setEmail("ogecks1@dagondesign.com");
        guest.setPhone("(202) 2528316");
        guest.setState("DC");
        guests.add(guest);
    }

    @Override
    public ArrayList<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest getGuestById(int id) throws DataException {
        return guests.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Guest getGuestByEmail(String guestEmail) throws DataException {
        return guests.stream().filter(i -> i.getEmail().equalsIgnoreCase(guestEmail)).findFirst().orElse(null);
    }
}