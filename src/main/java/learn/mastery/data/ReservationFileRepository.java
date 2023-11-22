package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private String directoryPath;
    private GuestRepository guestRepository;

    public ReservationFileRepository(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void setGuestRepository(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findByHost(Host host) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host.getId())))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, host));
                }
            }
        } catch (IOException ex) {
            //
        }
        return result;

    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHost(reservation.getHost());
        reservation.setId(reservations.size() + 1);
        reservations.add(reservation);
        writeAll(reservations, reservation.getHost());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHost(reservation.getHost());
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, reservation);
                writeAll(reservations, reservation.getHost());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        List<Reservation> reservations = findByHost(reservation.getHost());
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.remove(i);
                writeAll(reservations, reservation.getHost());
                return true;
            }
        }
        return false;
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s", reservation.getId(), reservation.getStart(), reservation.getEnd(), reservation.getGuest().getId(), reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, Host host) throws DataException {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStart(LocalDate.parse(fields[1]));
        result.setEnd(LocalDate.parse(fields[2]));
        result.setHost(host);
        Guest guest = guestRepository.getGuestById(Integer.parseInt(fields[3]));
        result.setGuest(guest);
        result.setTotal(new BigDecimal(fields[4]));
        return result;
    }

    private String getFilePath(String hostId) {
        return Paths.get(directoryPath, hostId + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, Host host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host.getId()))) {
            writer.println(HEADER);

            for (Reservation item : reservations) {
                writer.println(serialize(item));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
