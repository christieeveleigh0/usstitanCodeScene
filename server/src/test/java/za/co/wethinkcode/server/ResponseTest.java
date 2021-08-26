package za.co.wethinkcode.server;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
  @Test
  void generalResponse() {
    GeneralResponse generalResponse = new GeneralResponse("general response test");
    Gson gson = new Gson();
    String generalResponseString = gson.toJson(generalResponse);
    String jsonString = "{\"result\":\"ERROR\",\"data\":{\"message\":\"general response test\"}}";
    assertEquals(jsonString, generalResponseString);
  }

  @Test
  void Response() {
    List<Integer> coordinates = new ArrayList<>();
    coordinates.add(0);
    coordinates.add(0);
    Integer steps = 20;
    Integer reloadSeconds = 5;
    Integer repairSeconds = 10;
    Integer mineSeconds = 10;
    Integer hits = 5;
    World.Direction facing = World.Direction.NORTH;
    Integer bullets = 20;
    World.Mode mode = World.Mode.NORMAL;

    Map<String, Object> data = new HashMap<>();
    data.put("position", coordinates);
    data.put("visibility", steps);
    data.put("reload", reloadSeconds);
    data.put("repair", repairSeconds);
    data.put("mine", mineSeconds);
    data.put("shields", hits);

    Map<String, Object> state = new HashMap<>();
    state.put("position", coordinates);
    state.put("direction", facing);
    state.put("shields", hits);
    state.put("shots", bullets);
    state.put("status", mode);

    Response response = new Response(data, state);
    Gson gson = new Gson();
    String responseString = gson.toJson(response);
    String jsonString =
        "{\"result\":\"OK\",\"data\":{\"position\":[0,0],\"visibility\":20,\"reload\":5,\"repair\":10,\"mine\":"
            + " 10,\"shields\":5},\"state\":{\"position\":[0,0],\"direction\":\"NORTH\",\"shields\":5,\"shots\":20,\"status\":\"NORMAL\"}}";
    Map jsonMap = gson.fromJson(jsonString, Map.class);
    Map responseMap = gson.fromJson(responseString, Map.class);
    assertEquals(3, responseMap.size());
    assertEquals(jsonMap, responseMap);
  }
}
