package za.co.wethinkcode.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

  private final PrintStream standardOut = System.out;
  private final InputStream standardIn = System.in;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
    System.setIn(standardIn);
  }

  @Test
  void validHostNPort() {
    System.setIn(new ByteArrayInputStream("BATTLEFIELD-EARTH\n".getBytes()));
    Client.getHostNPort();

    assertEquals("localhost", Client.host);
    assertEquals(5056, Client.port);
  }

  @Test
  void invalidHostNPort() {
    System.setIn(new ByteArrayInputStream("MARS-DELTA-84\n Y\n".getBytes()));
    Client.getHostNPort();

    assertEquals("localhost", Client.host);
    assertEquals(5056, Client.port);
  }

  @Test
  void invalidLaunchStatement() {
    InputStream mockedInput = new ByteArrayInputStream("Launch\nlaunch sniper shaq".getBytes());
    System.setIn(mockedInput);

    String[] robot = Client.getRobot();

    assertEquals("sniper", robot[0]);
    assertEquals("shaq", robot[1]);
  }

  @Test
  void invalidLaunchMake() {
    InputStream mockedInput =
        new ByteArrayInputStream("Launch medic mandy\nlaunch Tanker Titi".getBytes());
    System.setIn(mockedInput);

    String[] robot = Client.getRobot();

    assertEquals("tanker", robot[0]);
    assertEquals("titi", robot[1]);
  }

  @Test
  void invalidLaunchOrderStatement() {
    InputStream mockedInput =
        new ByteArrayInputStream("Launch berri bomber\nlaunch BOMBER BERRI".getBytes());
    System.setIn(mockedInput);

    String[] robot = Client.getRobot();

    assertEquals("bomber", robot[0]);
    assertEquals("berri", robot[1]);
  }
}
