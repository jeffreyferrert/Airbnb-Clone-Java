package learn.mastery.ui;

import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        printHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void printReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservation was found with that email");
            return;
        }

        reservations.stream().sorted(Comparator.comparing(Reservation::getStart).thenComparing(Reservation::getEnd)).forEach(reservation -> io.printf("ID: %2s, %s - %s, Guest: %s, %s, Email: %s%n", reservation.getId(), reservation.getStart(), reservation.getEnd(), reservation.getGuest().getLastName(), reservation.getGuest().getFirstName(), reservation.getGuest().getEmail()));
    }

    public Reservation chooseReservation(Guest guest, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            if (reservation.getGuest().getId() == guest.getId() && reservation.getStart().isAfter(LocalDate.now())) {
                io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n", reservation.getId(), reservation.getStart(), reservation.getEnd(), reservation.getGuest().getLastName(), reservation.getGuest().getFirstName(), reservation.getGuest().getEmail());
                io.printf("Reservation ID: %s%n", reservation.getId());
                return reservation;
            }
        }

        io.println("No reservation was found with that email");
        return null;
    }

    //    -- make a Reservation from scratch, used in Controller.addReservation
    public Reservation makeReservation(Host host, Guest guest) {
        Reservation reservation = new Reservation();

        reservation.setHost(host);
        reservation.setGuest(guest);
        LocalDate start = getDate("Start");
        reservation.setStart(start);
        LocalDate end = getDate("End");
        reservation.setEnd(end);
        reservation.setTotal(calculateTotal(host, start, end));
        return reservation;
    }

    public Reservation update(Reservation reservation) {
        printHeader("Editing Reservation " + reservation.getId());
        Reservation new_reservation = new Reservation();

        new_reservation.setId(reservation.getId());
        new_reservation.setHost(reservation.getHost());
        new_reservation.setGuest(reservation.getGuest());
        LocalDate start = io.readLocalDate("Start (" + reservation.getStart() + "): ");
        new_reservation.setStart(start);
        LocalDate end = io.readLocalDate("End (" + reservation.getEnd() + "): ");
        new_reservation.setEnd(end);
        new_reservation.setTotal(calculateTotal(reservation.getHost(), start, end));
        return new_reservation;
    }

    private BigDecimal calculateTotal(Host host, LocalDate start, LocalDate end) {
        BigDecimal total = BigDecimal.ZERO;

        LocalDate date = start;
        while (!date.isAfter(end)) {
            BigDecimal rate;
            if (date.getDayOfWeek().getValue() >= 6) {
                rate = host.getWeekendRate();
            } else {
                rate = host.getStandardRate();
            }
            total = total.add(rate);
            date = date.plusDays(1);
        }
        return total;
    }

    public void printHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public boolean confirmSummary(Reservation reservation, String prompt) {
        printHeader("Summary");
        System.out.println("Start: " + reservation.getStart());
        System.out.println("End: " + reservation.getEnd());
        System.out.println("Total: " + reservation.getTotal());

        while (true) {
            String input = io.readRequiredString(prompt);
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            }
            io.println("[INVALID] Please enter 'y' or 'n'.");
        }
    }

    public void enterToContinue() {
        io.println("");
        io.readString("Press [Enter] to continue.");
    }

    public String getHostEmail() {
        return io.readRequiredEmail("Host Email: ");
    }

    public String getGuestEmail() {
        return io.readRequiredEmail("Guest Email: ");
    }

    public LocalDate getDate(String prompt) {
        return io.readLocalDate(prompt + " (MM/dd/yyyy): ");
    }

    public void displayErrors(List<String> messages) {
        io.println("");
        printHeader("Errors");
        for (String message : messages) {
            io.println(message);
        }
    }
}
