public class Clock extends Thread {
  // blank final
  final int CLOCK_INTERVAL;
  final int LAST_ORDER_TIME;
  final int CLOSING_TIME;
  // initialise
  int time = 0;

  // constructor
  Clock(int CLOCK_INTERVAL, int LAST_ORDER_TIME, int CLOSING_TIME) {
    setName("Clock");
    this.CLOCK_INTERVAL = CLOCK_INTERVAL;
    this.LAST_ORDER_TIME = LAST_ORDER_TIME;
    this.CLOSING_TIME = CLOSING_TIME;
  };

  @Override
  public void run() {
    // operating time
    for (; time < LAST_ORDER_TIME; time++)
      try {
        Thread.sleep(CLOCK_INTERVAL);
      } catch (Exception e) {};

    // notify last call
    synchronized (this) {
      this.notifyAll();
    };

    // last order time
    for (; time < CLOSING_TIME; time++)
      try {
        Thread.sleep(CLOCK_INTERVAL);
      } catch (Exception e) {};

    // notify closing
    synchronized (this) {
      this.notifyAll();
    };
  };

  // check if past last order
  public Boolean isLastOrder() {
    return (time >= LAST_ORDER_TIME);
  };

  // check if past closing
  public Boolean isClosing() {
    return (time >= CLOSING_TIME);
  };
};
