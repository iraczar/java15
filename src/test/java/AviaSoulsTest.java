import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    @Test
    public void testTicketCompareTo() {
        Ticket ticket1 = new Ticket("Moscow", "London", 20000, 12, 15); // цена 20000, время полёта 3 часа
        Ticket ticket2 = new Ticket("Moscow", "Paris", 15000, 10, 14);  // цена 15000, время полёта 4 часа

        // ticket2 должен быть меньше ticket1 по цене
        assertTrue(ticket2.compareTo(ticket1) < 0);
        assertTrue(ticket1.compareTo(ticket2) > 0);

        Ticket ticket3 = new Ticket("Moscow", "Berlin", 20000, 8, 12);
        // ticket1 и ticket3 должны быть равны по цене
        assertEquals(0, ticket1.compareTo(ticket3));
    }

    @Test
    public void testTicketTimeComparator() {
        Ticket ticket1 = new Ticket("Moscow", "London", 20000, 12, 15); // время полёта 3 часа
        Ticket ticket2 = new Ticket("Moscow", "Paris", 15000, 10, 14);  // время полёта 4 часа
        Ticket ticket3 = new Ticket("Moscow", "Berlin", 18000, 8, 13);  // время полёта 5 часов

        TicketTimeComparator comparator = new TicketTimeComparator();

        // ticket1 меньше ticket2 по времени полёта
        assertTrue(comparator.compare(ticket1, ticket2) < 0);
        // ticket2 больше ticket1 по времени полёта
        assertTrue(comparator.compare(ticket2, ticket1) > 0);
        // ticket2 меньше ticket3 по времени полёта
        assertTrue(comparator.compare(ticket2, ticket3) < 0);
    }

    @Test
    public void testSearchSortedByPrice() {
        AviaSouls manager = new AviaSouls();

        Ticket ticket1 = new Ticket("Moscow", "London", 30000, 12, 15);
        Ticket ticket2 = new Ticket("Moscow", "London", 20000, 10, 14);
        Ticket ticket3 = new Ticket("Moscow", "Berlin", 15000, 8, 10);
        Ticket ticket4 = new Ticket("Moscow", "London", 25000, 9, 13);
        Ticket ticket5 = new Ticket("London", "Moscow", 35000, 16, 19);

        manager.add(ticket1);
        manager.add(ticket2);
        manager.add(ticket3);
        manager.add(ticket4);
        manager.add(ticket5);

        Ticket[] result = manager.search("Moscow", "London");

        // Должно быть 3 билета
        assertEquals(3, result.length);

        // Проверяем сортировку по возрастанию цены
        assertEquals(20000, result[0].getPrice()); // ticket2
        assertEquals(25000, result[1].getPrice()); // ticket4
        assertEquals(30000, result[2].getPrice()); // ticket1
    }

    @Test
    public void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();

        Ticket ticket1 = new Ticket("Moscow", "London", 30000, 12, 15); // 3 часа
        Ticket ticket2 = new Ticket("Moscow", "London", 20000, 10, 14); // 4 часа
        Ticket ticket3 = new Ticket("Moscow", "London", 25000, 8, 11);  // 3 часа
        Ticket ticket4 = new Ticket("Moscow", "London", 35000, 9, 15);  // 6 часов
        Ticket ticket5 = new Ticket("London", "Moscow", 40000, 16, 20); // 4 часа

        manager.add(ticket1);
        manager.add(ticket2);
        manager.add(ticket3);
        manager.add(ticket4);
        manager.add(ticket5);

        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] result = manager.searchAndSortBy("Moscow", "London", comparator);

        // Должно быть 4 билета
        assertEquals(4, result.length);

        // Проверяем сортировку по времени полёта
        assertEquals(3, result[0].getFlightTime()); // ticket1 или ticket3 (оба 3 часа)
        assertEquals(3, result[1].getFlightTime()); // ticket1 или ticket3 (оба 3 часа)
        assertEquals(4, result[2].getFlightTime()); // ticket2
        assertEquals(6, result[3].getFlightTime()); // ticket4
    }

    @Test
    public void testSearchNotFound() {
        AviaSouls manager = new AviaSouls();

        Ticket ticket1 = new Ticket("Moscow", "London", 30000, 12, 15);
        Ticket ticket2 = new Ticket("Moscow", "Berlin", 20000, 10, 12);

        manager.add(ticket1);
        manager.add(ticket2);

        Ticket[] result = manager.search("Moscow", "Paris");

        // Не должно быть найденных билетов
        assertEquals(0, result.length);
    }

    @Test
    public void testEmptyManager() {
        AviaSouls manager = new AviaSouls();

        Ticket[] result = manager.search("Moscow", "London");

        assertEquals(0, result.length);
    }
}