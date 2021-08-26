package za.co.wethinkcode.client;

/**
 * The type Command.
 */
public abstract class Command {

  private final String name;
  private String argument;

  /**
   * Invokes a command on a Robot.
   *
   * @param robot the target of the command
   * @param comms the comms
   * @return the result after issuing the command
   */
  public abstract boolean execute(Robot robot, Comms comms);

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
   * Splits an instruction into a name and argument/s -"" if there are no arguments.
   *
   * @param instruction the command instruction for.
   * @return an instance of the command.
   */
  public static Command create(String instruction) {
    String[] args = instruction.toLowerCase().trim().split(" ");
    try {
      switch (args[0]) {
        case "help":
          return new HelpCommand();
        case "quit":
          return new QuitCommand();
        case "launch":
          return new LaunchCommand(args[1] + " " + args[2]);
        case "forward":
          return new ForwardCommand(args[1]);
        case "back":
          return new BackCommand(args[1]);
        case "look":
          return new LookCommand();
        case "repair":
          return new RepairCommand();
        case "fire":
          return new FireCommand();
        case "reload":
          return new ReloadCommand();
        case "mine":
          return new MineCommand();
        case "state":
          return new StateCommand();
        case "turn":
          if ((args[1].equals("right")) || args[1].equals("left")) {
            return new TurnCommand(args[1]);
          }
        default:
          throw new IllegalArgumentException("Unsupported command: " + instruction);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Incomplete command: " + instruction);
    }
  }
}
