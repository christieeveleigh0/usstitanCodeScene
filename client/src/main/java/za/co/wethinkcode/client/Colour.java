package za.co.wethinkcode.client;

/**
 * The type Colour.
 */
public class Colour {
  private final String reset = "\u001B[0m";

  /**
   * White string.
   *
   * @param text the text
   * @return the string
   */
  public String white(String text) {
    return "\u001b[37;1m" + text + reset;
  }

  /**
   * Red string.
   *
   * @param text the text
   * @return the string
   */
  public String red(String text) {
    return "\u001b[31;1m" + text + reset;
  }

  /**
   * Green string.
   *
   * @param text the text
   * @return the string
   */
  public String green(String text) {
    return "\u001b[32;1m" + text + reset;
  }

  /**
   * Yellow string.
   *
   * @param text the text
   * @return the string
   */
  public String yellow(String text) {
    return "\u001b[33;1m" + text + reset;
  }

  /**
   * Blue string.
   *
   * @param text the text
   * @return the string
   */
  public String blue(String text) {
    return "\u001b[34;1m" + text + reset;
  }

  /**
   * Purple string.
   *
   * @param text the text
   * @return the string
   */
  public String purple(String text) {
    return "\u001b[35;1m" + text + reset;
  }

  /**
   * Cyan string.
   *
   * @param text the text
   * @return the string
   */
  public String cyan(String text) {
    return "\u001b[36;1m" + text + reset;
  }
}
