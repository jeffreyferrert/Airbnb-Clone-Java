package learn.mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private Host host;
    private Guest guest;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal total;

    public int getId() {
        return id;
    }

    public Host getHost() {
        return host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
