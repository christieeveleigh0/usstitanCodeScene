package za.co.wethinkcode.client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import java.util.Properties;
import java.util.List;

/**
 * Instantiates a new Client class
 */
public class Client {
  /**
   * The constant clientSocket.
   */
  public static Socket clientSocket;
  /**
   * The constant host.
   */
  public static String host;
  /**
   * The constant port.
   */
  public static int port;
  /**
   * The constant launchCommand.
   */
  public static String launchCommand = "launch Sniper Shaq";
  /**
   * The constant leave.
   */
  public static final List<String> leave = Arrays.asList("quit", "exit", "off");
  /**
   * The constant colour.
   */
  public static Colour colour = new Colour();

  /**
   * the main function / starting point of the client class.
   *
   * @param args holds any additional arguments when launching the client. this is not used.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    playIntro();
    getHostNPort();

    try {
      T29Assistant t29Assistant = new T29Assistant(new Scanner(System.in));
      InetAddress address = InetAddress.getByName(host);
      t29Assistant.setDestination(address, port);
      clientSocket = t29Assistant.startConnection();
      if (clientSocket == null) {
        System.exit(0);
      }

      boolean shouldContinue = false;
      Command command;
      String[] battleBot = getRobot();
      Robot robot = t29Assistant.selectAvatar(battleBot[0], battleBot[1]);
      String requestToServer = launchCommand;
      String output = "";

      do {
        try {
          command = Command.create(requestToServer);
          shouldContinue = robot.handleCommand(command);

          output = robot.getOutput();
          System.out.println(output);

          if (!robot.getLaunched()) {
            battleBot = getRobot();
            robot = t29Assistant.selectAvatar(battleBot[0], battleBot[1]);
            requestToServer = launchCommand;
            continue;
          }

        } catch (IllegalArgumentException e) {
          System.out.println(
              colour.purple("<T11101> Sorry, I did not understand '" + requestToServer + "'."));
        }

        if (shouldContinue) {
          System.out.println(robot.whatsNext());
          requestToServer = scanner.nextLine();
        }
      } while (shouldContinue);

    } catch (Exception e) {
      e.printStackTrace();
    }

    playOutro();
  }

  /**
   * Displays the welcome splash screen on the terminal before the game begins.
   */
  public static void playIntro() {
    // TODO: add start date/time
    BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    g.setFont(new Font("Dialog", Font.PLAIN, 24));
    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics.drawString("RoboWorld       ", 6, 24);

    for (int y = 0; y < 32; y++) {
      StringBuilder sb = new StringBuilder();
      for (int x = 0; x < 144; x++)
        sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
      if (sb.toString().trim().isEmpty()) continue;
      System.out.println(colour.blue(String.valueOf(sb)));
    }

    System.out.println(
        colour.blue("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  F-W-B-W"));
    System.out.println(
        colour.blue(
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t~Created for the World by WeThinkCoder_'s~\n\n"));
  }

  /**
   * Sets the host and port to be used based on the users input.
   */
  public static void getHostNPort() {
    Scanner scanner = new Scanner(System.in);
    Properties worlds = new Properties();
    String retry;

    try {
      InputStream readWorlds = ClassLoader.getSystemResourceAsStream("worlds.properties");
      worlds.load(readWorlds);

    } catch (NullPointerException | IOException e) {
      System.out.println(
          colour.red(
              "<x><x><x><x><x>     ERROR 24     <x><x><x><x><x>\n"
                  + " Worlds database has been corrupted or deleted.\n"
                  + "  Contact Team29 to Restore system databases.\n"
                  + "<x><x><x><x><x>SYSTEM MALFUNCTION<x><x><x><x><x>"));
      System.exit(24);
    }

    System.out.println(
        colour.green(
            "*****************************************************************************\n"
                + "*******************************{    WORLDS     }*****************************\n"
                + "**[*killzone-kepler-62-f*]***[ BATTLEFIELD-EARTH ]***[**arena-proxima-b***]**\n"
                + "*******************************{    STATUS     }*****************************\n"
                + "**[*travel ban in effect*]***[       READY       ]***[*under"
                + " construction*]**\n"));

    boolean worldSet = false;
    while (!worldSet) {
      System.out.println(colour.cyan("v<<<<<< Enter the World we will be travelling to."));
      String world = scanner.nextLine().trim().toLowerCase();

      if (leave.contains(world)) {
        System.out.println(
            colour.purple(
                "<T11101> The game ends before it's even begun.\n"
                    + "        Setting course back HOME."));
        System.exit(0);
      } else if (world.equals("")) {
        world = "battlefield-earth";
      }

      switch (world) {
        case "battlefield-earth":
          host = worlds.getProperty("earthIP");
          port = Integer.parseInt(worlds.getProperty("earthPort"));
          worldSet = true;
          break;
        case "arena-proxima-b":
          // TODO : add host and port to properties.
        case "killzone-kepler-62-f":
          // TODO : add host and port to properties and remove below code.
          System.out.println(
              colour.purple(
                      "<T11101> Theres no signal coming from that world.\n"
                          + "        Would you like to set course to the default world?\n")
                  + colour.cyan("v<<<<<< Enter [Y] to use default or any key to try again."));
          retry = scanner.nextLine().trim();
          if (retry.equalsIgnoreCase("Y")) {
            host = worlds.getProperty("earthIP");
            port = Integer.parseInt(worlds.getProperty("earthPort"));
            worldSet = true;
          }
          break;
        default:
          System.out.println(
              colour.purple(
                      "<T11101> It appears we do not have that world on record.\n"
                          + "        Would you like to set course to the default world?\n")
                  + colour.cyan("v<<<<<< Enter [Y] to use default or any key to try again."));
          retry = scanner.nextLine().trim();
          if (retry.equalsIgnoreCase("Y")) {
            host = worlds.getProperty("earthIP");
            port = Integer.parseInt(worlds.getProperty("earthPort"));
            worldSet = true;
          }
      }
    }
  }

  /**
   * Get the Robot make and name.
   *
   * @return the robot object.
   */
  public static String[] getRobot() {
    Scanner scanner = new Scanner(System.in);
    List<String> validBots = Arrays.asList("sniper", "bomber", "tanker");
    System.out.println(
        colour.purple("\n\n<T11101> Please specify the BATTLE-BOT to send out.\n")
            + colour.green(
                "<><><><><> All BATTLE-BOTS ready to launch <><><><><>\n"
                    + "        Enter 'launch [BATTLE-BOT] [BOT-NAME].'\n"
                    + "       OR 'help' to get at list of BATTLE-BOTS.\n"
                    + "<><><><><><><><><><><><><><><><><><><><><><><><><><><>\n"));

    while (true) { // THIS MAKES SURE THAT LAUNCH (OR HELP) IS THE FIRST COMMAND ACCEPTED.
      System.out.println(colour.cyan("\n\nv<<<<<< Enter launch or help command."));
      String requestToServer = scanner.nextLine();
      String[] request = requestToServer.toLowerCase().trim().split(" ");

      if ((request[0].equals("launch")) && (request.length == 3)) {
        if ((validBots.contains(request[1].toLowerCase()))) {
          launchCommand = requestToServer;
          return (new String[] {request[1], request[2]});

        } else {
          System.out.println(
              colour.purple(
                  "<T11101> It appears we don't have that BATTLE-BOT on the ready.\n"
                      + "        Check your spelling or check the help menu."));
        }
      } else if (request[0].equals("launch")) {
        System.out.println(
            colour.purple(
                "<T11101> Invalid launch command given.\n"
                    + "        Use 'launch [BATTLE-BOT] [BOT-NAME]'."));
      } else if (request[0].equals("help")) {
        System.out.println(
            colour.green(
                "------------------------------ BATTLE-BOTS -----------------------------------\n"
                    + "------------------------------   SNIPER   "
                    + " -----------------------------------\n"
                    + " Best suited for defensive play, hiding behind obstacles and peaking out"
                    + " only \n"
                    + " to take shots at the enemy.\n"
                    + " STATES:\n"
                    + "  * Form factor:    Feather weight    --- 1 Shield  ---\n"
                    + "  * Weapon:         Barrett M82       --- 2 Bullets ---\n"
                    + "  * Launch command: launch sniper [name]\n"
                    + "------------------------------   BOMBER    "
                    + " ----------------------------------\n"
                    + " best suited for rouge play, a mix of running and hiding. Trapping the enemy"
                    + " in\n"
                    + " its mines.\n"
                    + " STATES:\n"
                    + "  * Form factor:    Light weight      --- 2 shields ---\n"
                    + "  * Weapon:         Land mine         ---    alot   ---\n"
                    + "  * Launch command: launch bomber [name]\n"
                    + "------------------------------   TANKER   "
                    + " -----------------------------------\n"
                    + " Best suited for offensive play, running around the map hunting down the"
                    + " enemy.\n"
                    + " STATES:\n"
                    + "  * Form factor:  Heavy weight      --- 3 shields ---\n"
                    + "  * Weapon:       Glock 17          --- 3 bullets ---\n"
                    + "  * Launch command: launch tanker [name]\n"
                    + "------------------------------------------------------------------------------\n"));

      } else if (leave.contains(request[0])) {
        System.out.println(
            colour.purple(
                "<T11101> The game ends before it's even begun.\n"
                    + "        Setting course back HOME."));
        System.exit(0);
      } else {
        System.out.println(
            colour.purple(
                "<T11101> You must first launch your BATTLE-BOT.\n"
                    + "        Check your command or check the help menu."));
      }
    }
  }

  /**
   * Displays the end game splash screen on the terminal after the game ends.
   */
  public static void playOutro() {
    // TODO: add end date/time

    BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    g.setFont(new Font("Dialog", Font.PLAIN, 24));
    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics.drawString("*RoboStats*", 6, 24);

    for (int y = 0; y < 32; y++) {
      StringBuilder sb = new StringBuilder();
      for (int x = 0; x < 144; x++)
        sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
      if (sb.toString().trim().isEmpty()) continue;
      System.out.println(colour.blue(String.valueOf(sb)));
    }
  }
}
