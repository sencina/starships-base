package config.reader;

import controller.ShipController;
import factory.EntityFactory;
import javafx.scene.input.KeyCode;
import model.KeyAction;
import movement.KeyMovement;
import movement.Mover;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import state.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private final String path;

    public ConfigManager(String path) {
        this.path = path;
    }

    public Map<String, List<KeyAction>> loadKeys() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("starship-0");
        return Map.of("starship-0", fillKeyActionListForId("starship-0", ja));
    }

    private List<KeyAction> fillKeyActionListForId(String id, JSONArray ja){

        List<KeyAction> list = new ArrayList<>();
        for (Object o : ja) {
            JSONObject keyAction = (JSONObject) o;
            list.add(new KeyAction(KeyCode.getKeyCode((String) keyAction.get("key")), KeyMovement.valueOf((String) keyAction.get("action"))));
        }

        return list;
    }

    public void saveKeys(Map<String, List<KeyAction>> map) throws IOException {

        JSONObject jo = new JSONObject();
        for (String key : map.keySet()) {
            JSONArray ja = new JSONArray();
            for (KeyAction action : map.get(key)) {
                JSONObject actionJo = new JSONObject();
                actionJo.put("key", action.key().toString());
                actionJo.put("action", action.action().toString());
                ja.add(actionJo);
            }
            jo.put(key, ja);
        }
        writeFile(jo.toJSONString());
    }

    public void saveState(GameState state) throws IOException {
        writeFile(state.toJson().toJSONString());
    }

    private JSONArray createEntityArray(List<Mover> entities) {
        JSONArray ja = new JSONArray();
        for (Mover mover : entities) {
            JSONObject jo = new JSONObject();
            jo.put("mover",mover.toJson());
            ja.add(jo);
        }
        return ja;
    }

    private void writeFile(String string) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(string);
        writer.close();
    }

    public GameState readState() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(path));
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("entities");
        JSONArray jas = (JSONArray) jo.get("ships");

        return new GameState((double) jo.get("width"), (double) jo.get("height"), fillEntityList(ja), fillShipList(jas));

    }

    private List<ShipController> fillShipList(JSONArray jas) {
        List<ShipController> list = new ArrayList<>();
        for (Object o : jas) {
            JSONObject jo = (JSONObject) o;
            list.add(EntityFactory.createShipControllerFromJson(jo));
        }
        return list;
    }

    private List<Mover> fillEntityList(JSONArray ja) {

        List<Mover> list = new ArrayList<>();
        for (Object o : ja) {
            JSONObject entity = (JSONObject) o;
            list.add(EntityFactory.createMoverFromJson(entity));
        }

        return list;
    }
}
