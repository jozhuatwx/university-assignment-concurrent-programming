import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Seats {
  Clock clock;
  int total;
  Semaphore seat;
  LinkedList<Customer> customers = new LinkedList<Customer>();

  Seats(Clock clock, int total) {
    this.clock = clock;
    this.total = total;
    this.seat = new Semaphore(total);
  };

  public void takeSeat(Customer customer) {
    try {
      // take seat
      seat.acquire();
    } catch (Exception e) {};

    // add customer to list
    customers.add(customer);
  };

  public void leaveSeat(Customer customer) {
    // remove customer from list
    customers.remove(customer);

    try {
      // release seat
      seat.release();
    } catch (Exception e) {};
  };

  // check if seats have any customers
  public Boolean hasCustomers() {
    return (seat.availablePermits() != total ? true : false);
  };
};
