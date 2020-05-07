public class Clock extends Thread {
  int time = 0;
  int lastOrder;
  int closing;

  // constructor
  Clock(int LAST_ORDER_TIME, int CLOSING_TIME) {
    this.setName("Clock");
    this.lastOrder = LAST_ORDER_TIME;
    this.closing = CLOSING_TIME;
  };

  @Override
  public void run() {
    // operating time
    for (; time < lastOrder; time++)
      try {
        Thread.sleep(1000);
      } catch (Exception e) {};

    // notify last call
    synchronized (this) {
      this.notifyAll();
    };

    // last order time
    for (; time < closing; time++)
      try {
        Thread.sleep(1000);
      } catch (Exception e) {};

    // notify closing
    synchronized (this) {
      this.notifyAll();
    };
  };

  // check if past last order
  public Boolean isLastOrder() {
    return (time >= lastOrder);
  };

  // check if past closing
  public Boolean isClosing() {
    return (time >= closing);
  };
};
