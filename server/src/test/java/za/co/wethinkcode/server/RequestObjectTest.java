package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestObjectTest {

  @Test
  void requestFields() {
    String requestString = "{\"robot\":\"berri\",\"command\":\"kill\",\"arguments\":[birdbot]}";
    RequestObject requestObject = new RequestObject(requestString);
    assertEquals("berri", requestObject.getRobot());
    assertEquals("kill", requestObject.getCommand());
    String[] args = requestObject.getArguments();
    assertEquals("[\"birdbot\"]", args[0]);
  }
}
