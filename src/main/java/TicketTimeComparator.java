import java.util.Comparator;

public class TicketTimeComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket t1, Ticket t2) {
        // Сравниваем по времени полёта
        int time1 = t1.getFlightTime();
        int time2 = t2.getFlightTime();
        return Integer.compare(time1, time2);
    }
}