package za.co.wethinkcode.server;

import java.util.*;

/**
 * The type World.
 */
public class World {

  /**
   * The enum Direction.
   */
  public enum Direction {
    /**
     * North direction.
     */
    NORTH,
    /**
     * East direction.
     */
    EAST,
    /**
     * South direction.
     */
    SOUTH,
    /**
     * West direction.
     */
    WEST
  }

  /**
   * The enum Update response.
   */
  public enum UpdateResponse {
    /**
     * Hit update response.
     */
    HIT,
    /**
     * Miss update response.
     */
    MISS,
    /**
     * Success update response.
     */
    SUCCESS,
    /**
     * Failed outside world update response.
     */
    FAILED_OUTSIDE_WORLD,
    /**
     * Failed obstructed update response.
     */
    FAILED_OBSTRUCTED,
    /**
     * Failed square update response.
     */
    FAILED_SQUARE,
    /**
     * Failed pit update response.
     */
    FAILED_PIT,
    /**
     * Failed mine update response.
     */
    FAILED_MINE,
    /**
     * Failed robot update response.
     */
    FAILED_ROBOT,
  }

  /**
   * The enum Mode.
   */
  public enum Mode {
    /**
     * Reload mode.
     */
    RELOAD,
    /**
     * Repair mode.
     */
    REPAIR,
    /**
     * Setmine mode.
     */
    SETMINE,
    /**
     * Normal mode.
     */
    NORMAL,
    /**
     * Fled mode.
     */
    FLED,
    /**
     * Purged mode.
     */
    PURGED,
    /**
     * Dead mode.
     */
    DEAD
  }

  /**
   * The Random.
   */
  static Random random = new Random();
  private final Position topLeft;
  private final Position bottomRight;
  private final int visibility;
  private final int repairTime;
  private final int reloadTime;
  private final int mineSet;
  private final int mineHit;
  private final int shieldStrength;
  private final int shots;
  private final World.Direction startDirection;
  private Map<String, Robot> robotMap;
  private Map<String, ClientHandler> clientMap;
  private Maze maze;
  private Map<String, Robot> fugitives;
  private Map<String, Robot> deadRobotMap;
  private int space;

  /**
   * Instantiates a new World.
   *
   * @param width          the width
   * @param height         the height
   * @param visibility     the visibility
   * @param repairTime     the repair time
   * @param reloadTime     the reload time
   * @param mineSet        the mine set
   * @param shieldStrength the shield strength
   */
  public World(
      int width,
      int height,
      int visibility,
      int repairTime,
      int reloadTime,
      int mineSet,
      int shieldStrength) {
    this.topLeft = new Position(-(width / 2), height / 2);
    this.bottomRight = new Position(width / 2, -(height / 2));
    this.repairTime = repairTime;
    this.reloadTime = reloadTime;
    this.mineSet = mineSet;
    this.shieldStrength = shieldStrength;
    this.mineHit = shieldStrength;
    this.visibility = visibility;
    this.shots = visibility;
    this.startDirection = Direction.NORTH;
    this.robotMap = new HashMap<>();
    this.clientMap = new HashMap<>();
    this.maze = new RandomMaze(this);
    this.fugitives = new HashMap<>();
    this.deadRobotMap = new HashMap<>();
  }

  /**
   * Gets top left.
   *
   * @return the top left
   */
  public Position getTopLeft() {
    return topLeft;
  }

  /**
   * Gets bottom right.
   *
   * @return the bottom right
   */
  public Position getBottomRight() {
    return bottomRight;
  }

  /**
   * Gets visibility.
   *
   * @return the visibility
   */
  public int getVisibility() {
    return visibility;
  }

  /**
   * Gets repair time.
   *
   * @return the repair time
   */
  public int getRepairTime() {
    return repairTime;
  }

  /**
   * Gets reload time.
   *
   * @return the reload time
   */
  public int getReloadTime() {
    return reloadTime;
  }

  /**
   * Gets mine set.
   *
   * @return the mine set
   */
  public int getMineSet() {
    return mineSet;
  }

  /**
   * Gets shield strength.
   *
   * @return the shield strength
   */
  public int getShieldStrength() {
    return shieldStrength;
  }

  /**
   * Gets shots.
   *
   * @return the shots
   */
  public int getShots() {
    return this.shots;
  }

  /**
   * Gets start direction.
   *
   * @return the start direction
   */
  public World.Direction getStartDirection() {
    return startDirection;
  }

  /**
   * Gets robot map.
   *
   * @return the robot map
   */
  public Map<String, Robot> getRobotMap() {
    return robotMap;
  }

  /**
   * Add robot.
   *
   * @param name  the name
   * @param robot the robot
   */
  public void addRobot(String name, Robot robot) {
    this.robotMap.put(name, robot);
  }

  /**
   * Gets robot.
   *
   * @param name the name
   * @return the robot
   */
  public Robot getRobot(String name) {
    return robotMap.get(name);
  }

  /**
   * Remove robot.
   *
   * @param name the name
   */
  public void removeRobot(String name) {
    this.robotMap.remove(name);
  }

  /**
   * Gets client map.
   *
   * @return the client map
   */
  public Map<String, ClientHandler> getClientMap() {
    return clientMap;
  }

  /**
   * Add client.
   *
   * @param name   the name
   * @param client the client
   */
  public void addClient(String name, ClientHandler client) {
    clientMap.put(name, client);
  }

  /**
   * Gets client.
   *
   * @param name the name
   * @return the client
   */
  public ClientHandler getClient(String name) {
    return clientMap.get(name);
  }

  /**
   * Gets maze.
   *
   * @return the maze
   */
  public Maze getMaze() {
    return maze;
  }

  /**
   * Sets maze.
   *
   * @param maze the maze
   */
  public void setMaze(Maze maze) {
    this.maze = maze;
  }

  /**
   * Gets fugitives.
   *
   * @return the fugitives
   */
  public Map<String, Robot> getFugitives() {
    return fugitives;
  }

  /**
   * Gets fugitive.
   *
   * @param name the name
   * @return the fugitive
   */
  public Robot getFugitive(String name) {
    return fugitives.get(name);
  }

  /**
   * Add fugitive.
   *
   * @param robot the robot
   */
  public void addFugitive(Robot robot) {
    fugitives.put(robot.getName(), robot);
  }

  /**
   * Gets dead robot map.
   *
   * @return the dead robot map
   */
  public Map<String, Robot> getDeadRobotMap() {
    return deadRobotMap;
  }

  /**
   * Is space boolean.
   *
   * @return the boolean
   */
  public boolean isSpace() {
    return this.space < 8;
  }

  /**
   * End robot.
   *
   * @param robot the robot
   */
  public void endRobot(Robot robot) {
    String name = robot.getName();
    robotMap.remove(name);
    deadRobotMap.put(name, robot);
    freeSpace();
  }

  /**
   * Gets population.
   *
   * @return the population
   */
  public int getPopulation() {
    return space;
  }

  /**
   * Sets space.
   *
   * @param space the space
   */
  public void setSpace(int space) {
    this.space = space;
  }

  /**
   * Crowd space.
   */
  public void crowdSpace() {
    if (isSpace()) {
      this.space++;
    }
  }

  /**
   * Free space.
   */
  public void freeSpace() {
    if (this.space > 0) {
      this.space--;
    }
  }

  /**
   * Random position position.
   *
   * @return the position
   */
  public Position randomPosition() {
    List<Obstacle> obstacles = maze.getObstacles();
    while (true) {
      int x = random.nextInt((bottomRight.getX() - topLeft.getX()) + 1) + topLeft.getX();
      int y = random.nextInt((topLeft.getY() - bottomRight.getY()) + 1) + bottomRight.getY();
      Position startPosition = new Position(x, y);
     if (found(obstacles, startPosition)) {
        return startPosition;
     }
    }
  }

  /**
   * Found boolean.
   *
   * @param obstacles     the obstacles
   * @param startPosition the start position
   * @return the boolean
   */
  public boolean found(List<Obstacle> obstacles, Position startPosition) {
    for (Obstacle obstacle : obstacles) {
      if (obstacle.blocksPosition(startPosition)) {
        return false;
      }
    }
    return isNewPositionAllowed(startPosition);
  }

  /**
   * Update position world . update response.
   *
   * @param robot   the robot
   * @param nrSteps the nr steps
   * @param looker  the looker
   * @param shooter the shooter
   * @return the world . update response
   */
  public World.UpdateResponse updatePosition(Robot robot, int nrSteps, boolean looker, boolean shooter) {
    Position robotPosition = robot.getPosition();
    Direction robotDirection = robot.getCurrentDirection();
    int newX = robotPosition.getX();
    int newY = robotPosition.getY();
    Position newPosition = getNewPosition(robotDirection, newX, newY, nrSteps);
    Tracker tracker = new Tracker(robotDirection);

    List<Map<String, Object>> lookerList = new ArrayList<>();
    List<Obstacle> obstacles = maze.getObstacles();
    if (looker) {
      for (Obstacle obstacle : obstacles) {
        if (obstacle.blocksPath(robotPosition, newPosition)) {
          String type = obstacle.getType();
          int distance = tracker.getStepDistance(robotDirection, newX, newY, obstacle.getBottomLeftX(), obstacle.getBottomLeftY());
          if (!type.equals("MINE") && distance > 2) {
            lookerList.add(tracker.getObjectMap(type, distance));
          }
        }
        robot.addSeen(lookerList);
        return UpdateResponse.SUCCESS;
      }
    }

      if (shooter) {
        if (tracker.killShot(obstacles, this, robot, newPosition)) {
          return UpdateResponse.HIT;
        } else {
          return UpdateResponse.MISS;
        }
      }

      if (!isNewPositionAllowed(newPosition)) {
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
      }

      UpdateResponse update = tracker.track(obstacles, robotPosition, newPosition);

      switch (update) {
        case FAILED_PIT:
          robot.setStatus(World.Mode.DEAD);
          break;
        case FAILED_MINE:
          executeDamage(robot, "mine");
          break;
        default:
          robot.setPosition(newPosition);
      }
      return update;
  }

  /**
   * Gets new position.
   *
   * @param direction the direction
   * @param newX      the new x
   * @param newY      the new y
   * @param nrSteps   the nr steps
   * @return the new position
   */
  public Position getNewPosition(Direction direction, int newX, int newY, int nrSteps) {
    switch (direction) {
      case NORTH:
        newY += nrSteps;
        break;
      case EAST:
        newX += nrSteps;
        break;
      case SOUTH:
        newY -= nrSteps;
        break;
      default:
        newX -= nrSteps;
    }
    return new Position(newX, newY);
  }

  /**
   * Update direction.
   *
   * @param robot     the robot
   * @param turnRight the turn right
   */
  public void updateDirection(Robot robot, boolean turnRight) {
    World.Direction robotDirection = robot.getCurrentDirection();
    World.Direction update;
    if (turnRight) {
      switch (robotDirection) {
        case NORTH:
          update = World.Direction.EAST;
          break;
        case EAST:
          update = World.Direction.SOUTH;
          break;
        case SOUTH:
          update = World.Direction.WEST;
          break;
        default:
          update = World.Direction.NORTH;
      }
    } else {
      switch (robotDirection) {
        case NORTH:
          update = World.Direction.WEST;
          break;
        case EAST:
          update = World.Direction.NORTH;
          break;
        case SOUTH:
          update = World.Direction.EAST;
          break;
        default:
          update = World.Direction.SOUTH;
      }
    }
    robot.setCurrentDirection(update);
  }

  /**
   * Is new position allowed boolean.
   *
   * @param position the position
   * @return the boolean
   */
  public boolean isNewPositionAllowed(Position position) {
    return position.isIn(getTopLeft(), getBottomRight());
  }

  /**
   * Handle command object.
   *
   * @param command the command
   * @return the object
   */
  public Object handleCommand(Command command) {
    return command.execute(this);
  }

  /**
   * Execute damage.
   *
   * @param robot  the robot
   * @param weapon the weapon
   */
  public void executeDamage(Robot robot, String weapon) {
    if (robot.getStatus().equals(Mode.SETMINE) && weapon.equals("firearm")) {
      report(robot, robot.getName());
      robot.setStatus(World.Mode.DEAD);
      endRobot(robot);
      return;
    }
    int damage = 0;
    if (weapon.equals("mine")) {
      damage = robot.getShields() - mineHit;
    } else if (weapon.equals("firearm")) {
      damage = robot.getShields() - 1;
    }
    robot.setShields(damage);
    if (damage < 0) {
      report(robot, robot.getName());
      robot.setStatus(World.Mode.DEAD);
      endRobot(robot);
    }
  }

  /**
   * Report.
   *
   * @param robot the robot
   * @param name  the name
   */
  public void report(Robot robot, String name) {
    Command command = Command.create(new RequestObject("{\"robot\":\"" + name + "\",\"command\":\"state\",\"arguments\":[]}"));
    command.execute(robot);
    Object response = robot.handleCommand(command);
    ResponseJson responseJson = new ResponseJson(response);
    ClientHandler client = getClient(name);
    client.sendMessage(responseJson);
  }

  /**
   * Gets help.
   *
   * @return the help
   */
  public String getHelp() {
    return (
            "| See Wiki for detailed info"
                    + " ----------------------------------------------------------------------|\n"
                    + "| https://github.com/wtc-cohort-2020/0029-robot-worlds/wiki"
                    + " ---------------------------------------|\n"
                    +
                    "|--------------------------------------------------------------------------------------------------|\n"
                    + "|----------------------------------- World Command List"
                    + " -------------------------------------------|\n"
                    +
                    "|--------------------------------------------------------------------------------------------------|\n"
                    + ": [HELP]     displays what you're reading right now\n"
                    + ": [QUIT]     ends the world\n"
                    + ": [ROBOTS]   exposes the names and states of the robots\n"
                    + ": [PURGE]    deports the named robot e.g. 'PURGE titibot9000' will remove titibot9000 from the world\n"
                    + ": [DUMP]     displays the state of the world\n"
                    +
                    "|--------------------------------------------------------------------------------------------------|\n"
                    +
                    "|--------------------------------------------------------------------------------------------------|\n");
  }

  /**
   * Gets quit.
   *
   * @return the quit
   */
  public String getQuit() {
    return ("(((Quitting)))...BattleWorld Apocalypse...(((gnittiuQ)))");
  }

  @Override
  public String toString() {
    return "(((Dumped)))" +
            "\n Battle~Field~Earth" +
            "\n ~~~~~~~~~~~~~~~~~~" +
            "\n topLeft~" + topLeft +
            "\n bottomRight~" + bottomRight +
            "\n visibility~" + visibility +
            "\n repairTime~" + repairTime +
            "\n reloadTime~" + reloadTime +
            "\n mineSet~" + mineSet +
            "\n mineHit~" + mineHit +
            "\n shieldStrength~" + shieldStrength +
            "\n shots~" + shots +
            "\n startDirection~" + startDirection +
            "\n ~robotMap~\n"
            + robotMap +
            "\n ~clientMap~\n"
            + clientMap +
            "\n ~maze~\n"
            + maze +
            "\n space~" + space +
            "\n ~fugitives~\n"
            + fugitives +
            "\n ~deadRobots~\n"
            + deadRobotMap +
            "\n(((depmuD)))";
  }
}
