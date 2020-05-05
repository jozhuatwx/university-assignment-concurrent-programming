public class Clock extends Thread {
  int time = 0;
  int lastOrder;
  int closing;

  // constructor
  Clock(int lastOrder, int closing) {
    this.setName("Clock");
    this.lastOrder = lastOrder;
    this.closing = closing;
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

    // last call time
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
