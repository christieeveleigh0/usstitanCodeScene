package za.co.wethinkcode.server;

/**
 * The type Square obstacle.
 */
public class SquareObstacle extends Obstacle {
  private final int size = 5;

  /**
   * Instantiates a new Square obstacle.
   *
   * @param bottomLeftX the bottom left x
   * @param bottomLeftY the bottom left y
   */
  public SquareObstacle(int bottomLeftX, int bottomLeftY) {
    super("OBSTACLE", bottomLeftX, bottomLeftY);
  }

  @Override
  public int getSize() {
    return size;
  }
}
