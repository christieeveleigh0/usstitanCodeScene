package za.co.wethinkcode.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * The type Client handler.
 */
public class ClientHandler extends Thread {
  /**
   * The Valid commands.
   */
  final List<String> VALID_COMMANDS =

          Arrays.asList(
                  "launch", "state", "look", "forward", "back", "turn", "repair", "reload", "mine", "fire",
                  "quit", "exit");

  /**
   * The Client socket.
   */
  final Socket clientSocket;
  /**
   * The In.
   */
  final BufferedReader in;
  /**
   * The Out.
   */
  final PrintWriter out;
  /**
   * The World.
   */
  final World world;

  /**
   * Instantiates a new Client handler.
   *
   * @param clientSocket the client socket
   * @param in           the in
   * @param out          the out
   * @param world        the world
   */
  public ClientHandler(Socket clientSocket, BufferedReader in, PrintWriter out, World world) {
    this.clientSocket = clientSocket;
    this.in = in;
    this.out = out;
    this.world = world;
  }

  /**
   * Send message.
   *
   * @param responseJson the response json
   */
  public void sendMessage(ResponseJson responseJson) {
    out.println(responseJson.getResponseString());
  }

  @Override
  public void run() {
    String requestFromClient;
    RequestObject requestObject;
    String instruction;
    Command command;
    String name;
    Robot robot;
    Object response;
    ResponseJson responseJson;

    while (true) {

      try {

        requestFromClient = in.readLine();

        requestObject = new RequestObject(requestFromClient);

        instruction = requestObject.getCommand();

        boolean validCommand = VALID_COMMANDS.contains(instruction);
        boolean quitter = instruction.equals("quit") || instruction.equals("exit");
        boolean launcher = instruction.equals("launch");
        boolean stater = instruction.equals("state") || instruction.equals("repair") || instruction.equals("reload");
        boolean miner = instruction.equals("mine");
        name = requestObject.getRobot();
        if (stater) {
          robot = world.getRobot(name);
          command = Command.create(requestObject);
          response = robot.handleCommand(command);
        } else if (validCommand) {
          command = Command.create(requestObject);
          response = world.handleCommand(command);
        } else {
          response = new GeneralResponse("Unsupported command");
        }
        responseJson = new ResponseJson(response);
        boolean entranceDenied =
                responseJson
                        .toString()
                        .equals(
                                "{\"result\":\"ERROR\",\"data\":{\"message\":\"Too many of you in this"
                                        + " world\"}}")
                        || responseJson
                        .toString()
                        .equals(
                                "{\"result\":\"ERROR\",\"data\":{\"message\":\"No more space in this world"
                                        + " world\"}}");

        out.println(responseJson.getResponseString());

        if (quitter) {
          System.out.println(this.clientSocket + " wants to leave the world.");
          break;
        }

        if (launcher && entranceDenied) {
          System.out.println(this.clientSocket + " is crowding this world.");
          break;
        } else if (launcher) {
          world.addClient(name, this);
        }

      } catch (NullPointerException | IOException e) {
        break;
      }
    }
    System.out.println("\n))) " + clientSocket + " has left (((");
    try {
      this.clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      this.in.close();
      this.out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Disconnect.
   *
   * @throws IOException the io exception
   */
  public void disconnect() throws IOException {
    if (in != null) {
      in.close();
    }
    if (out != null) {
      out.close();
    }
    if (clientSocket != null) {
      clientSocket.close();
    }
  }
}
