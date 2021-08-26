package za.co.wethinkcode.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The T29-Assist.
 */
public class T29Assistant extends Assistant {
  private Scanner scanner;
  private Socket socket;
  /**
   * The Colour.
   */
  Colour colour = new Colour();

  /**
   * Instantiates a new T29 assistant.  @throws UnknownHostException the unknown host exception
   */
  public T29Assistant() throws UnknownHostException {
    this(new Scanner(System.in));
  }

  /**
   * Instantiates a new T29 assistant with a scanner object.
   *
   * @param scanner stdin scanner
   * @throws UnknownHostException the unknown host exception
   */
  public T29Assistant(Scanner scanner) throws UnknownHostException {
    super();
    this.scanner = scanner;
  }

  @Override
  public void setDestination(InetAddress address, int port) {
    setAddress(address);
    setPort(port);
    System.out.println(
        colour.purple("<T11101> Locked to Address: " + address + " Port: " + port + "."));
  }

  @Override
  public Robot selectAvatar(String makeR, String nameR) {
    int shields = getShields(makeR);
    int ammo = getAmmo(makeR);

    return new Robot(makeR, nameR, shields, ammo);
  }

  /**
   * Gets shields.
   *
   * @param make the make
   * @return the shields
   */
  public int getShields(String make) {
    switch (make) {
      case "sniper":
        return 3;
      case "bomber":
        return 6;
      default:
        return 9;
    }
  }

  /**
   * Gets ammo.
   *
   * @param make the make
   * @return the ammo
   */
  public int getAmmo(String make) {
    switch (make) {
      case "sniper":
        return 2;
      case "bomber":
        return 0;
      default:
        return 3;
    }
  }

  @Override
  public Socket startConnection() throws IOException {
    System.out.println(colour.green("\n\n\n<<<:>>> Connection Request Sent <<<:>>>"));
    while (true) {
      try {
        this.setSocket(new Socket(getAddress(), getPort()));
        System.out.println(colour.green("<-<-<:>->-> Connection established  <-<-<:>->->"));
        return getSocket();
      } catch (ConnectException e) {
        System.out.println(
            colour.yellow(
                "\n<!><!> There seems to be no signs of intelligent lifeforms <!><!>\n"
                    + "             Make sure the World Servers are running.\n"
                    + "<!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!>\n\n"));
        System.out.println(
            colour.purple("<T1101> Resend connection signals?")
                + colour.green(">>> press [any key] then [enter] for YES or type NO to exit:"));
        String input = this.scanner.next().toLowerCase();
        if ("no".equals(input)) {
          System.out.println(colour.blue("Player's gonna play..."));
          System.out.println(colour.blue("\n\t\t\t\t\t\t\t\t\t\tBaller's gonna ball..."));
          System.out.println(
              colour.blue(
                  "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<QU1TT3R> Quitters gonna quit!"));
          return null;
        }
      }
    }
  }

  /**
   * Gets socket for connecting to the server.
   *
   * @return the socket
   */
  public Socket getSocket() {
    return this.socket;
  }

  /**
   * Sets socket for connecting to the server.
   *
   * @param socket the socket
   */
  public void setSocket(Socket socket) {
    this.socket = socket;
  }
}
