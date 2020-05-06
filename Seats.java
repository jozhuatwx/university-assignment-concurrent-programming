import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Seats {
  Clock clock;
  Semaphore seat;
  LinkedList<Customer> customers = new LinkedList<Customer>();

  // constructor
  Seats(Clock clock, int seat) {
    this.clock = clock;
    this.seat = new Semaphore(seat);
  };

  public void takeSeat(Customer customer) {
    try {
      // take seat
      seat.acquire();
      Thread.sleep((new Random().nextInt(2) + 1) * 100);
    } catch (Exception e) {};

    // add customer to list
    customers.add(customer);
  };

  public void leaveSeat(Customer customer) {
    // remove customer from list
    customers.remove(customer);

    try {
      Thread.sleep((new Random().nextInt(2) + 1) * 100);
      // release seat
      seat.release();
    } catch (Exception e) {};
  };
};
