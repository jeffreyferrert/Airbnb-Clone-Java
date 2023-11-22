package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public class Controller {
    private View view;
    private ReservationService reservationService;
    private GuestService guestService;
    private HostService hostService;

    public Controller(View view, ReservationService reservationService, GuestService guestService, HostService hostService) {
        this.view = view;
        this.reservationService = reservationService;
        this.guestService = guestService;
        this.hostService = hostService;
    }

    public void run() {
        view.printHeader("Welcome to Don't Wreck My House");
        try {
            runMenu();
        } catch (DataException ex) {
            System.out.println("A critical error occurred:");
            System.out.println(ex.getMessage());
        }

        view.printHeader("Goodbye.");
    }

    private void runMenu() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewByHost();
                    break;
                case MAKE_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewByHost() throws DataException {
        view.printHeader(MainMenuOption.VIEW_RESERVATIONS.getMessage());

        Host host = getHost();
        view.printHeader("Charon: " + host.getCity() + ", " + host.getState());
        List<Reservation> reservations = reservationService.findByHost(host);
        view.printReservations(reservations);
        view.enterToContinue();
    }

    private void addReservation() throws DataException {
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());

        Guest guest = getGuest();
        Host host = getHost();
        view.printHeader("Charon: " + host.getCity() + ", " + host.getState());
        List<Reservation> reservations = reservationService.findByHost(host);
        view.printReservations(reservations);

        Reservation reservation = view.makeReservation(host, guest);
        boolean confirmed = view.confirmSummary(reservation, "Is this okay? [y/n]: ");

        if (confirmed) {
            Result<Reservation> result = reservationService.add(reservation);
            if (!result.isSuccess()) {
                view.displayErrors(result.getErrorMessages());
            } else {
                view.printHeader("Success");
                System.out.println("Reservation " + reservation.getId() + " created.");
            }
        }
        view.enterToContinue();
    }

    //    -- coordinates between service and view to edit a Reservation
    private void updateReservation() throws DataException {
        view.printHeader(MainMenuOption.EDIT_RESERVATION.getMessage());

        Guest guest = getGuest();
        Host host = getHost();
        view.printHeader("Charon: " + host.getCity() + ", " + host.getState());
        List<Reservation> reservations = reservationService.findByHost(host);

        Reservation oldReservation = view.chooseReservation(guest, reservations);
        if (oldReservation == null) {
            return;
        }
        Reservation newReservation = view.update(oldReservation);
        boolean confirmed = view.confirmSummary(newReservation, "Is this okay? [y/n]: ");

        if (confirmed) {
            Result<Reservation> result = reservationService.update(newReservation);
            if (!result.isSuccess()) {
                view.displayErrors(result.getErrorMessages());
            } else {
                view.printHeader("Success");
                System.out.println("Reservation " + newReservation.getId() + " updated.");
            }
        }
        view.enterToContinue();
    }

    private void deleteReservation() throws DataException {
        view.printHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());

        Guest guest = getGuest();
        Host host = getHost();
        view.printHeader("Charon: " + host.getCity() + ", " + host.getState());
        List<Reservation> reservations = reservationService.findByHost(host);

        Reservation oldReservation = view.chooseReservation(guest, reservations);
        if (oldReservation == null) {
            return;
        }
        Result<Reservation> result = reservationService.delete(oldReservation);

        if (!result.isSuccess()) {
            view.displayErrors(result.getErrorMessages());
        } else {
            view.printHeader("Success");
            System.out.println("Reservation " + oldReservation.getId() + " cancelled.");
        }
        view.enterToContinue();
    }

    private Host getHost() throws DataException {
        Host host = null;
        boolean hostRetrieved = false;

        while (!hostRetrieved) {
            String hostEmail = view.getHostEmail();
            host = hostService.getHostById(hostEmail);

            if (host == null) {
                System.out.println("Host not found with email: " + hostEmail);
            } else {
                hostRetrieved = true;
            }
        }
        return host;
    }

    private Guest getGuest() throws DataException {
        Guest guest = null;
        boolean guestRetrieved = false;

        while (!guestRetrieved) {
            String guestEmail = view.getGuestEmail();
            guest = guestService.getGuestByEmail(guestEmail);

            if (guest == null) {
                System.out.println("Guest not found with email: " + guestEmail);
            } else {
                guestRetrieved = true;
            }
        }
        return guest;
    }
}
