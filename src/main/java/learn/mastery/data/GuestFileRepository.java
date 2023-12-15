package learn.mastery.data;

import learn.mastery.models.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GuestFileRepository implements GuestRepository {
    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            //
        }
        return result;
    }

    public Guest getGuestById(int guestId) {
        return findAll().stream().filter(i -> i.getId() == guestId).findFirst().orElse(null);
    }

    public Guest getGuestByEmail(String guestEmail) {
        return findAll().stream().filter(i -> i.getEmail().equalsIgnoreCase(guestEmail)).findFirst().orElse(null);
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }
}
