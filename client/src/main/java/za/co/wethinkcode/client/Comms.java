package za.co.wethinkcode.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static za.co.wethinkcode.client.Client.clientSocket;

/**
 * The type Comms.
 */
public class Comms {
  private PrintWriter out;
  private BufferedReader in;
  private JsonNode request;

  /**
   * Instantiates a new Comms.
   */
  public Comms() {
    {
      try {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Instantiates a new Comms.
   *
   * @param socket the socket
   */
  public Comms(Socket socket) {
    {
      try {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    try {
      return (in.readLine());
    } catch (IOException e) {
      return ("Connection lost.");
    }
  }

  /**
   * Gets the request object.
   *
   * @return the request.
   */
  public JsonNode getRequest() {
    return this.request;
  }

  /**
   * Sets the request object.
   *
   * @param request the request
   */
  public void setRequest(JsonNode request) {
    this.request = request;
  }

  /**
   * Sends the request to the server.
   */
  public void sendRequest() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      String prettyString = mapper.writeValueAsString(this.request);
      out.println(prettyString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  /**
   * Receives a response from the server.
   *
   * @return the response from the server.
   */
  public JsonNode getResponse() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readTree(in.readLine());

    } catch (IOException e) {
      ObjectNode node = mapper.createObjectNode();
      node.put("result", "FAILED");
      return node;
    }
  }
}
