package za.co.wethinkcode.server;

/**
 * The type Open field maze.
 */
public class OpenFieldMaze extends Maze {

  /**
   * Instantiates a new Open field maze.
   *
   * @param world the world
   */
  public OpenFieldMaze(World world) {
    super(world);
    this.generateObstacles();
  }

  @Override
  public void generateObstacles() {
    Obstacle teo = new EdgeObstacle("top");
    Obstacle beo = new EdgeObstacle("bottom");
    Obstacle leo = new EdgeObstacle("left");
    Obstacle reo = new EdgeObstacle("right");
  }
}
