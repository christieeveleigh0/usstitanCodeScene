package za.co.wethinkcode.server;

/**
 * The type Robot obstacle.
 */
public class RobotObstacle extends Obstacle {
  private final int size = 3;

  /**
   * Instantiates a new Robot obstacle.
   *
   * @param name        the name
   * @param bottomLeftX the bottom left x
   * @param bottomLeftY the bottom left y
   */
  public RobotObstacle(String name, int bottomLeftX, int bottomLeftY) {
    super("ROBOT", name, bottomLeftX, bottomLeftY);
  }

  @Override
  public int getSize() {
    return size;
  }
}
