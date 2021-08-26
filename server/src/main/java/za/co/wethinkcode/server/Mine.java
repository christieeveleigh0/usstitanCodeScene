package za.co.wethinkcode.server;

/**
 * The type Mine.
 */
public class Mine extends Obstacle {
  private final int size = 1;

  /**
   * Instantiates a new Mine.
   *
   * @param bottomLeftX the bottom left x
   * @param bottomLeftY the bottom left y
   */
  public Mine(int bottomLeftX, int bottomLeftY) {
    super("MINE", bottomLeftX, bottomLeftY);
  }

  @Override
  public int getSize() {
    return size;
  }
}
