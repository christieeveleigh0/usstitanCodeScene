package za.co.wethinkcode.server;

import java.util.Map;

/**
 * The type Response.
 */
public class Response {

  private final String result = "OK";
  private Map<String, Object> data;
  private Map<String, Object> state;

  /**
   * Instantiates a new Response.
   *
   * @param data  the data
   * @param state the state
   */
  Response(Map<String, Object> data, Map<String, Object> state) {
    this.data = data;
    this.state = state;
  }

  /**
   * Gets data.
   *
   * @return the data
   */
  public Map<String, Object> getData() {
    return data;
  }

  /**
   * Gets state.
   *
   * @return the state
   */
  public Map<String, Object> getState() {
    return state;
  }
}
