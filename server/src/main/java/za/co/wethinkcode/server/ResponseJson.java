package za.co.wethinkcode.server;

import com.google.gson.Gson;

/**
 * The type Response json.
 */
public class ResponseJson {

  /**
   * The Gson.
   */
  static final Gson gson = new Gson();
  private String responseString;

  /**
   * Instantiates a new Response json.
   *
   * @param response the response
   */
  public ResponseJson(Object response) {
    this.responseString = gson.toJson(response);
  }

  /**
   * Gets response string.
   *
   * @return the response string
   */
  public String getResponseString() {
    return responseString;
  }
}
