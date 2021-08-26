package za.co.wethinkcode.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Properties;

/**
 * The type Server.
 */
public class Server {

  /**
   * The Server socket.
   */
  static ServerSocket serverSocket;
  /**
   * My socket.
   */
  static Socket mySocket;
  /**
   * The Client socket.
   */
  static Socket clientSocket;
  /**
   * The In.
   */
  static BufferedReader in;
  /**
   * The Out.
   */
  static PrintWriter out;

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IOException the io exception
   */
  public static void main(String[] args) throws IOException {
    Server server = new Server();
    int port;

    if (args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
        server.start(port);
      } catch (NumberFormatException e) {
        String asteroid = e.getClass().getSimpleName().substring(0, 12);
        System.out.println(
            "(!(x(E)x)!) World ended due to " + asteroid + " Asteroid Event (!(x(E)x)!)");
        System.out.println(
            "\nRerun the Server using a port number or leave blank to use the system default.");
      }
    } else {
      server.start(5056);
    }
  }

  /**
   * Start.
   *
   * @param port the port
   * @throws IOException the io exception
   */
  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    World world = setWorld();

    try {
      InetAddress ip = InetAddress.getByName("localhost");
      mySocket = new Socket(ip, 5056);
      mySocket = serverSocket.accept();

      PrintWriter serverOut = new PrintWriter(System.out, true);
      BufferedReader serverIn = new BufferedReader(new InputStreamReader(System.in));

      Thread thread = new WorldCommandHandler(serverSocket, mySocket, serverIn, serverOut, world);

      thread.start();

    } catch (Exception e) {
      if (mySocket != null) {
        mySocket.close();
      }
      System.out.println("caught server");
    }

    while (true) {
      clientSocket = null;

      try {
        clientSocket = serverSocket.accept();
        System.out.println("))) The following Simian has joined your world: \n" + clientSocket + " (((");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Thread thread = new ClientHandler(clientSocket, in, out, world);
        thread.start();

      } catch (Exception e) {
        break;
      }
    }
    stop();
  }

  /**
   * Sets world.
   *
   * @return the world
   * @throws IOException the io exception
   */
  public World setWorld() throws IOException {
    String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    String worldConfigPath = rootPath + "world.properties";

    Properties worldProps = new Properties();
    try {
      worldProps.load(new FileInputStream(worldConfigPath));
    } catch (FileNotFoundException e) {
      System.out.print("Your 'world.properties' file is missing or misplaced.");
    }

    int width = Integer.parseInt(worldProps.getProperty("width"));
    int height = Integer.parseInt(worldProps.getProperty("height"));
    int visibility = Integer.parseInt(worldProps.getProperty("visibility"));
    int repairTime = Integer.parseInt(worldProps.getProperty("repairTime"));
    int reloadTime = Integer.parseInt(worldProps.getProperty("reloadTime"));
    int mineSet = Integer.parseInt(worldProps.getProperty("mineSet"));
    int shieldStrength = Integer.parseInt(worldProps.getProperty("shieldStrength"));

    return new World(width, height, visibility, repairTime, reloadTime, mineSet, shieldStrength);
  }

  /**
   * Stop.
   *
   * @throws IOException the io exception
   */
  public void stop() throws IOException {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
      if (clientSocket != null) {
        clientSocket.close();
      if (serverSocket != null) {
        serverSocket.close();
      }
      }
    }
}
