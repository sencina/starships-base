package config.reader;

import controller.ShipController;
import factory.EntityFactory;
import javafx.scene.input.KeyCode;
import movement.KeyMovement;
import movement.Mover;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import state.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private final static String STATE_PATH = System.getProperty("user.dir") + "/app/src/main/java/config/state.json";
    private final static String KEYS_PATH = System.getProperty("user.dir") + "/app/src/main/java/config/keys.json";

    public static void saveKeys(Map<String, Map<KeyMovement, KeyCode>> map) throws IOException {

        JSONArray ja = new JSONArray();
        for (String key : map.keySet()) {
            JSONObject jo = new JSONObject();
            jo.put("id", key);
            jo.put("map", parseMapToString(map.get(key)));
            ja.add(jo);
        }
        writeFile(ja.toJSONString(), KEYS_PATH);
    }

    private static Map<KeyMovement,String> parseMapToString(Map<KeyMovement, KeyCode> keyMovementKeyCodeMap) {
        Map<KeyMovement,String> toReturn = new HashMap<>();
        for (KeyMovement keyMovement : keyMovementKeyCodeMap.keySet()) {
            toReturn.put(keyMovement, keyMovementKeyCodeMap.get(keyMovement).getName());
        }
        return toReturn;
    }

    public static void saveState(GameState state) throws IOException {
        writeFile(state.toJson().toJSONString(),STATE_PATH);
    }

    private static JSONArray createEntityArray(List<Mover> entities) {
        JSONArray ja = new JSONArray();
        for (Mover mover : entities) {
            JSONObject jo = new JSONObject();
            jo.put("mover",mover.toJson());
            ja.add(jo);
        }
        return ja;
    }

    private static void writeFile(String string, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(string);
        writer.close();
    }

    public static GameState readState() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(STATE_PATH));
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("entities");
        JSONArray jas = (JSONArray) jo.get("ships");

        return new GameState((double) jo.get("width"), (double) jo.get("height"), fillEntityList(ja), fillShipList(jas), new ArrayList<>());

    }

    private static List<ShipController> fillShipList(JSONArray jas) {
        List<ShipController> list = new ArrayList<>();
        for (Object o : jas) {
            JSONObject jo = (JSONObject) o;
            list.add(EntityFactory.createShipControllerFromJson(jo));
        }
        return list;
    }

    private static List<Mover> fillEntityList(JSONArray ja) {

        List<Mover> list = new ArrayList<>();
        for (Object o : ja) {
            JSONObject entity = (JSONObject) o;
            list.add(EntityFactory.createMoverFromJson(entity));
        }

        return list;
    }

    @NotNull
    public static Map<String,Map<KeyMovement,KeyCode>> readBindings() throws IOException, ParseException {

        JSONArray ja = (JSONArray) (new JSONParser().parse( new FileReader(KEYS_PATH)));
        Map<String,Map<KeyMovement,KeyCode>> map = new HashMap<>();
        for (Object o : ja) {
            JSONObject jo = (JSONObject) o;
            map.put((String) jo.get("id"), parseStringToMap((JSONObject) jo.get("map")));
        }
        return map;
    }

    private static Map<KeyMovement, KeyCode> parseStringToMap(JSONObject map) {
        Map<KeyMovement,KeyCode> toReturn = new HashMap<>();
        toReturn.put(KeyMovement.ACCELERATE, KeyCode.getKeyCode((String) map.get("ACCELERATE")));
        toReturn.put(KeyMovement.TURN_LEFT, KeyCode.getKeyCode((String) map.get("TURN_LEFT")));
        toReturn.put(KeyMovement.TURN_RIGHT, KeyCode.getKeyCode((String) map.get("TURN_RIGHT")));
        toReturn.put(KeyMovement.SHOOT, KeyCode.getKeyCode((String) map.get("SHOOT")));
        toReturn.put(KeyMovement.STOP, KeyCode.getKeyCode((String) map.get("STOP")));
        return toReturn;
    }
}
