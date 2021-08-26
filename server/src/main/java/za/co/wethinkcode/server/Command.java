package za.co.wethinkcode.server;

/**
 * The type Command.
 */
public abstract class Command {
  private final String name;
  private String argument;

  /**
   * Execute object.
   *
   * @param object the object
   * @return the object
   */
  public abstract Object execute(Object object);

  /**
   * Instantiates a new Command along with the command's name.
   *
   * @param name the command's name
   */
  Command(String name) {
    this.name = name.trim().toLowerCase();
    this.argument = "";
  }

  /**
   * Constructor specifying the new Command's name and argument.
   *
   * @param name     the command's name
   * @param argument the command argument
   */
  Command(String name, String argument) {
    this(name);
    this.argument = argument.trim();
  }

  /**
   * Gets the command name.
   *
   * @return the name
   */
  public String getName() { // <2>
    return name;
  }

  /**
   * Gets the command argument.
   *
   * @return the argument
   */
  public String getArgument() {
    return this.argument;
  }

  /**
   * Create command.
   *
   * @param requestObject the request object
   * @return the command
   */
  public static Command create(RequestObject requestObject) {
    String name = requestObject.getRobot();
    String command = requestObject.getCommand();
    String[] args = requestObject.getArguments();
    switch (command) {
        // Robot commands
      case "launch":
        String make = args[0].substring(2, args[0].length() - 1);
        String clientShields = args[1] + "";
        String clientShots = args[2].substring(0, args[2].length() - 1) + "";
        return new LaunchCommand(make + " " + name + " " + clientShields + " " + clientShots);
      case "state":
        return new StateCommand();
      case "exit":
      case "quit":
        return new ExitCommand(name);
      case "look":
        return new LookCommand(name);
      case "forward":
      case "back":
        String steps = args[0].substring(1, args[0].length() - 1) + "";
        if (command.equals("forward")) {
          return new ForwardCommand(name + " " + steps);
        }
        return new BackCommand(name + " " + steps);
      case "turn":
        String direction = args[0].substring(2, args[0].length() - 2);
        return new TurnCommand(name + " " + direction);
      case "repair":
        return new RepairCommand();
      case "reload":
        return new ReloadCommand();
      case "mine":
        return new MineCommand(name);
      case "fire":
        return new FireCommand(name);
      default:
        throw new IllegalArgumentException("Unsupported command: " + command);
    }
  }
}
