package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.Map;

/**
 * The type General response.
 */
public class GeneralResponse {

  private String result;
  private Map<String, String> data;

  /**
   * Instantiates a new General response.
   *
   * @param message the message
   */
  public GeneralResponse(String message) {
    this(message, "ERROR");
  }

  /**
   * Instantiates a new General response.
   *
   * @param message the message
   * @param result  the result
   */
  public GeneralResponse(String message, String result) {
    this.data = Collections.singletonMap("message", message);
    this.result = result;
  }

  /**
   * Gets result.
   *
   * @return the result
   */
  public String getResult() {
    return result;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return this.data.get("message");
  }
}
