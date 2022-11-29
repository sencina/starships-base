package config.manager;

import controller.ShipController;
import factory.JsonFactory;
import javafx.scene.input.KeyCode;
import movement.KeyMovement;
import movement.Mover;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import state.GameState;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFileReader {

    private final String path;

    public MyFileReader(String path) {
        this.path = path;
    }

    public GameState readGameState() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("entities");
        JSONArray jas = (JSONArray) jo.get("ships");
        JSONArray jat = (JSONArray) jo.get("points");

        return new GameState((double) jo.get("width"), (double) jo.get("height"), fillEntityList(ja), fillShipList(jas), new ArrayList<>(), createMap(jat));

    }

    private Map<String, Integer> createMap(JSONArray jat) {
        Map<String, Integer> map = new HashMap<>();
        for (Object o : jat) {
            JSONObject jo = (JSONObject) o;
            map.put((String) jo.get("id"), (int) (long) jo.get("points"));
        }
        return map;
    }

    private List<ShipController> fillShipList(JSONArray jas) {
        List<ShipController> list = new ArrayList<>();
        for (Object o : jas) {
            JSONObject jo = (JSONObject) o;
            list.add(JsonFactory.createShipControllerFromJson(jo));
        }
        return list;
    }

    private List<Mover> fillEntityList(JSONArray ja) {

        List<Mover> list = new ArrayList<>();
        for (Object o : ja) {
            JSONObject entity = (JSONObject) o;
            list.add(JsonFactory.createMoverFromJson(entity));
        }

        return list;
    }

    @NotNull
    public Map<String,Map<KeyMovement, KeyCode>> readBindings() throws IOException, ParseException {

        JSONArray ja = (JSONArray) (new JSONParser().parse( new FileReader(path)));
        Map<String,Map<KeyMovement,KeyCode>> map = new HashMap<>();
        for (Object o : ja) {
            JSONObject jo = (JSONObject) o;
            map.put((String) jo.get("id"), parseStringToMap((JSONObject) jo.get("map")));
        }
        return map;
    }

    private Map<KeyMovement, KeyCode> parseStringToMap(JSONObject map) {
        Map<KeyMovement,KeyCode> toReturn = new HashMap<>();
        toReturn.put(KeyMovement.ACCELERATE, KeyCode.getKeyCode((String) map.get("ACCELERATE")));
        toReturn.put(KeyMovement.TURN_LEFT, KeyCode.getKeyCode((String) map.get("TURN_LEFT")));
        toReturn.put(KeyMovement.TURN_RIGHT, KeyCode.getKeyCode((String) map.get("TURN_RIGHT")));
        toReturn.put(KeyMovement.SHOOT, KeyCode.getKeyCode((String) map.get("SHOOT")));
        toReturn.put(KeyMovement.STOP, KeyCode.getKeyCode((String) map.get("STOP")));
        return toReturn;
    }
}
