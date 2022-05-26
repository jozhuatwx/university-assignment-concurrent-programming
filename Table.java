import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Table {
  // blank final
  final Semaphore NUM_OF_SEAT;
  // initialise
  LinkedList<Customer> customers = new LinkedList<Customer>();

  // constructor
  Table(int NUM_OF_SEAT) {
    this.NUM_OF_SEAT = new Semaphore(NUM_OF_SEAT);
  };

  public void takeSeat(Customer customer) {
    try {
      // take seat
      NUM_OF_SEAT.acquire();
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
      NUM_OF_SEAT.release();
    } catch (Exception e) {};
  };
};
