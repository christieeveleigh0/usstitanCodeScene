package za.co.wethinkcode.client;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistantTest {

  private final PrintStream standardOut = System.out;
  private final InputStream standardIn = System.in;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  public Colour colour = new Colour();

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
  @Order(1)
  void setHostAndPort() throws UnknownHostException {
    T29Assistant t29 = new T29Assistant();
    InetAddress address = InetAddress.getByName("localhost");
    t29.setDestination(address, 7357);
    assertEquals(address, t29.getAddress());
    assertEquals(7357, t29.getPort());
    assertEquals(
        colour.purple("<T11101> Locked to Address: localhost/127.0.0.1 Port: 7357.").trim(),
        outputStreamCaptor.toString().trim());
  }

  @Test
  @Order(2)
  void validSelectionSniper() throws UnknownHostException {
    T29Assistant t29 = new T29Assistant();
    Robot robotA = new Robot("sniper", "HAL", 3, 2);
    Robot robotB = t29.selectAvatar("sniper", "HAL");

    assertEquals(robotA.getName(), robotB.getName());
    assertEquals(robotA.getMake(), robotB.getMake());
    assertEquals(robotA.getShield(), robotB.getShield());
    assertEquals(robotA.getAmmo(), robotB.getAmmo());
  }

  @Test
  @Order(3)
  void validSelectionTanker() throws UnknownHostException {
    T29Assistant t29 = new T29Assistant();
    Robot robotA = new Robot("tanker", "HAL", 9, 3);
    Robot robotB = t29.selectAvatar("tanker", "HAL");

    assertEquals(robotA.getName(), robotB.getName());
    assertEquals(robotA.getMake(), robotB.getMake());
    assertEquals(robotA.getShield(), robotB.getShield());
    assertEquals(robotA.getAmmo(), robotB.getAmmo());
  }

  @Test
  @Order(4)
  void validSelectionBomber() throws UnknownHostException {
    T29Assistant t29 = new T29Assistant();
    Robot robotA = new Robot("bomber", "HAL", 6, 0);
    Robot robotB = t29.selectAvatar("bomber", "HAL");

    assertEquals(robotA.getName(), robotB.getName());
    assertEquals(robotA.getMake(), robotB.getMake());
    assertEquals(robotA.getShield(), robotB.getShield());
    assertEquals(robotA.getAmmo(), robotB.getAmmo());
  }

  @Test
  @Order(5)
  void unsuccessfulConnection() throws IOException {
    InputStream mockedInput = new ByteArrayInputStream("no\n".getBytes());
    System.setIn(mockedInput);
    Assistant t29 = new T29Assistant();
    assertNull(t29.startConnection());
  }

  @Test
  @Order(6)
  void successfulConnection() throws IOException {
    InetAddress address = InetAddress.getByName("localhost");
    Assistant t29 = new T29Assistant();
    t29.setAddress(address);
    t29.setPort(5000);
    TestServer testServer = new TestServer();
    Thread task = new Thread(testServer);
    task.start();
    try {
      TimeUnit.SECONDS.sleep(1); // This gives the server time to startup.
    } catch (InterruptedException ignored) {
    }
    t29.startConnection();

    assertEquals(
        colour.green("\n\n\n<<<:>>> Connection Request Sent <<<:>>>")
            + "\n"
            + colour.green("<-<-<:>->-> Connection established  <-<-<:>->->")
            + "\n",
        outputStreamCaptor.toString());
  }

  private static class TestServer implements Runnable {
    ServerSocket serverSocket;
    Socket clientSocket;

    public void Start(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
    }

    public void run() {
      TestServer test = new TestServer();
      try {
        test.Start(5000);
        test.closeQuietly();
      } catch (IOException ignored) {
      }
    }

    public void closeQuietly() throws IOException {
      clientSocket.close();
      serverSocket.close();
    }
  }
}
