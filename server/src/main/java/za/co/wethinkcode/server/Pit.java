package za.co.wethinkcode.server;

/**
 * The type Pit.
 */
public class Pit extends Obstacle {

  private final int size = 5;

  /**
   * Instantiates a new Pit.
   *
   * @param bottomLeftX the bottom left x
   * @param bottomLeftY the bottom left y
   */
  public Pit(int bottomLeftX, int bottomLeftY) {
    super("PIT", bottomLeftX, bottomLeftY);
  }

  @Override
  public int getSize() {
    return size;
  }

}
