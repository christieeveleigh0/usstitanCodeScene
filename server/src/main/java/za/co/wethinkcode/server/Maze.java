package za.co.wethinkcode.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Maze.
 */
public abstract class Maze {
  private World world;
  private List<Obstacle> edgeObstacles;
  private List<Obstacle> squareObstacles;
  private List<Obstacle> pitObstacles;
  private List<Obstacle> mineObstacles;
  private List<Obstacle> robotObstacles;

  /**
   * Instantiates a new Maze.
   *
   * @param world the world
   */
  public Maze(World world) {
    this.world = world;
    this.squareObstacles = new ArrayList<>();
    this.pitObstacles = new ArrayList<>();
    this.mineObstacles = new ArrayList<>();
    this.robotObstacles = new ArrayList<>();
    this.edgeObstacles = Arrays.asList(new EdgeObstacle("top"), new EdgeObstacle("right"), new EdgeObstacle("bottom"), new EdgeObstacle("left"));
  }

  /**
   * Generate obstacles.
   */
  public abstract void generateObstacles();

  /**
   * Gets obstacles.
   *
   * @return the obstacles
   */
  public List<Obstacle> getObstacles() {
    List<Obstacle> obstacles = new ArrayList<>();
    obstacles.addAll(squareObstacles);
    obstacles.addAll(pitObstacles);
    obstacles.addAll(mineObstacles);
    obstacles.addAll(robotObstacles);
    obstacles.addAll(edgeObstacles);
    return obstacles;
  }

  /**
   * Gets world.
   *
   * @return the world
   */
  public World getWorld() {
    return world;
  }

  /**
   * Gets square obstacles.
   *
   * @return the square obstacles
   */
  public List<Obstacle> getSquareObstacles() {
    return squareObstacles;
  }

  /**
   * Add square obstacle.
   *
   * @param squareObstacle the square obstacle
   */
  public void addSquareObstacle(Obstacle squareObstacle) {
    squareObstacles.add(squareObstacle);
  }

  /**
   * Gets pit obstacles.
   *
   * @return the pit obstacles
   */
  public List<Obstacle> getPitObstacles() {
    return pitObstacles;
  }

  /**
   * Add pit obstacle.
   *
   * @param pitObstacle the pit obstacle
   */
  public void addPitObstacle(Obstacle pitObstacle) {
    pitObstacles.add(pitObstacle);
  }

  /**
   * Gets mine obstacles.
   *
   * @return the mine obstacles
   */
  public List<Obstacle> getMineObstacles() {
    return mineObstacles;
  }

  /**
   * Add mine obstacle.
   *
   * @param mineObstacle the mine obstacle
   */
  public void addMineObstacle(Obstacle mineObstacle) {
    mineObstacles.add(mineObstacle);
  }

  /**
   * Add robot obstacle.
   *
   * @param robotObstacle the robot obstacle
   */
  public void addRobotObstacle(Obstacle robotObstacle) {
    robotObstacles.add(robotObstacle);
  }

  /**
   * Update robots.
   *
   * @param name the name
   * @param x    the x
   * @param y    the y
   */
  public void updateRobots(String name, int x, int y) {
    for (Obstacle robot : robotObstacles) {
      if (robot.getName().equals(name)) {
        robot.setBottomLeftX(x);
        robot.setBottomLeftY(y);
        return;
      }
    }
  }

  @Override
  public String toString() {
    return "Maze{" +
            "\n squareObstacles=" + squareObstacles +
            "\n pitObstacles=" + pitObstacles +
            "\n mineObstacles=" + mineObstacles +
            "\n robotObstacles=" + robotObstacles +
            '}';
  }
}
