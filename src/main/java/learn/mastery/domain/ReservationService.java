package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private GuestRepository guestRepository;
    private HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }


    public List<Reservation> findByHost(Host host) throws DataException {
        return reservationRepository.findByHost(host);
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        boolean updated = reservationRepository.update(reservation);
        if (!updated) {
            result.addErrorMessage("Try again, it was not updated");
        }
        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        if (!reservationRepository.delete(reservation)) {
            result.addErrorMessage("Reservation doesn't exists");
        }
        if (reservation.getStart().isBefore(LocalDate.now()) || reservation.getEnd().isBefore(LocalDate.now())) {
            result.addErrorMessage("Old reservations can't be delete.");
        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        if (reservation.getStart() == null || reservation.getStart().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date is required and must be in the future.");
        }
        if (reservation.getEnd() == null || reservation.getEnd().isBefore(LocalDate.now()) || reservation.getEnd().isBefore(reservation.getStart())) {
            result.addErrorMessage("End date is required, must be in the future, and after the start date.");
        }

        List<Reservation> existingReservations = reservationRepository.findByHost(reservation.getHost());
        for (Reservation existingReservation : existingReservations) {
            if (existingReservation.getId() != reservation.getId() && doDatesOverlap(existingReservation, reservation)) {
                result.addErrorMessage("Reservation dates overlap with an existing reservation.");
                break;
            }
        }
        return result;
    }

    private boolean doDatesOverlap(Reservation existingReservation, Reservation newReservation) {
        LocalDate existingStartDate = existingReservation.getStart();
        LocalDate existingEndDate = existingReservation.getEnd();
        LocalDate newStartDate = newReservation.getStart();
        LocalDate newEndDate = newReservation.getEnd();

        return (newStartDate.isEqual(existingStartDate) || newStartDate.isAfter(existingStartDate)) && (newStartDate.isBefore(existingEndDate) || newStartDate.isEqual(existingEndDate)) || (existingStartDate.isEqual(newStartDate) || existingStartDate.isAfter(newStartDate)) && (existingStartDate.isBefore(newEndDate) || existingStartDate.isEqual(newEndDate));
    }

}
