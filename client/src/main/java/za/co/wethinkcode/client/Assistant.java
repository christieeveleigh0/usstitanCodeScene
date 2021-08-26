package za.co.wethinkcode.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The type Assistant.
 */
public abstract class Assistant {
  private InetAddress address;
  private int port;

  /**
   * Setup Assistant.  @throws UnknownHostException the unknown host exception
   */
  Assistant() throws UnknownHostException {
    this.address = InetAddress.getByName("localhost");
    this.port = 5000;
  }

  /**
   * Sets the host and port.
   *
   * @param address the host.
   * @param port    the port.
   */
  public abstract void setDestination(InetAddress address, int port);

  /**
   * Sets the make and name of a Robot.
   *
   * @param makeR the make r
   * @param nameR the name r
   * @return the Robot
   */
  public abstract Robot selectAvatar(String makeR, String nameR);

  /**
   * Connects to the server.
   *
   * @return the socket used for connection
   * @throws IOException the io exception
   */
  public abstract Socket startConnection() throws IOException;

  /**
   * Gets host.
   *
   * @return the host
   */
  public InetAddress getAddress() {
    return this.address;
  }

  /**
   * Sets host.
   *
   * @param address the host
   */
  public void setAddress(InetAddress address) {
    this.address = address;
  }

  /**
   * Gets port.
   *
   * @return the port
   */
  public int getPort() {
    return this.port;
  }

  /**
   * Sets port.
   *
   * @param port the port
   */
  public void setPort(int port) {
    this.port = port;
  }
}
