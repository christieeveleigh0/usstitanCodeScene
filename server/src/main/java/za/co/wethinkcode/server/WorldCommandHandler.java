package za.co.wethinkcode.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type World command handler.
 */
class WorldCommandHandler extends Thread {
  /**
   * The Server in.
   */
  final BufferedReader serverIn;
  /**
   * The Server out.
   */
  final PrintWriter serverOut;
  /**
   * The My socket.
   */
  final Socket mySocket;
  /**
   * The Server socket.
   */
  final ServerSocket serverSocket;
  /**
   * The World.
   */
  final World world;

  /**
   * Instantiates a new World command handler.
   *
   * @param serverSocket the server socket
   * @param mySocket     the my socket
   * @param serverIn     the server in
   * @param serverOut    the server out
   * @param world        the world
   */
  public WorldCommandHandler(ServerSocket serverSocket, Socket mySocket, BufferedReader serverIn, PrintWriter serverOut, World world) {
    this.serverSocket = serverSocket;
    this.mySocket = mySocket;
    this.serverIn = serverIn;
    this.serverOut = serverOut;
    this.world = world;
  }

  @Override
  public void run() {
    String myRequest;
    String myResponse;
    serverOut.println("\ntype 'help' for the World Command Menu\n\n");
    while (true) {

      try {

        myRequest = serverIn.readLine();

        if (myRequest.equals("quit")) {
         serverOut.println(new QuitCommandI().execute(world));
          break;
        }

        switch (myRequest) {
          case "help":
            serverOut.println(new HelpCommandI().execute(world));
            break;
          case "robots":
            serverOut.println(new RobotsCommandI().execute(world));
            break;
          case "dump":
            serverOut.println(new DumpCommandI().execute(world));
            break;
          default:
            if (myRequest.startsWith("purge")) {
              serverOut.println(new PurgeCommandI(myRequest.substring(5)).execute(world));
              break;
            }
            serverOut.println("(((No command))) '" + myRequest + "' is not a World Command (((dnammoc oN)))");
            break;
        }
      } catch (IOException ignored) {
      }
    }
    try {
      if (mySocket != null) {
        mySocket.close();
      }
      if (serverSocket != null) {
        serverSocket.close();
      }
      if (serverIn != null) {
        serverIn.close();
      }
      if (serverOut != null) {
        serverOut.close();
      }
    } catch (IOException | NullPointerException e) {
      System.out.println("Done");
    }
  }
}
