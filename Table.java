import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Table {
  Semaphore seat;
  LinkedList<Customer> customers = new LinkedList<Customer>();

  // constructor
  Table(int NUM_OF_SEAT) {
    this.seat = new Semaphore(NUM_OF_SEAT);
  };

  public void takeSeat(Customer customer) {
    try {
      // take seat
      seat.acquire();
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 3) * 100);
    } catch (Exception e) {};

    // add customer to list
    customers.add(customer);
  };

  public void leaveSeat(Customer customer) {
    // remove customer from list
    customers.remove(customer);

    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1, 3) * 100);
      // release seat
      seat.release();
    } catch (Exception e) {};
  };
};
