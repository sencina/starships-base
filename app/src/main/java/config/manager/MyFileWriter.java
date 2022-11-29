package config.manager;

import javafx.scene.input.KeyCode;
import movement.KeyMovement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyFileWriter {

    private final String path;

    public MyFileWriter(String path) {
        this.path = path;
    }

    public void write(JSONObject jo) throws IOException {
        write(jo.toJSONString());
    }

    private void write(String content) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
    }

    public void saveKeys(Map<String, Map<KeyMovement, KeyCode>> map) throws IOException {

        JSONArray ja = new JSONArray();
        for (String key : map.keySet()) {
            JSONObject jo = new JSONObject();
            jo.put("id", key);
            jo.put("map", parseMapToString(map.get(key)));
            ja.add(jo);
        }
        write(ja.toJSONString());
    }

    private static Map<KeyMovement,String> parseMapToString(Map<KeyMovement, KeyCode> keyMovementKeyCodeMap) {
        Map<KeyMovement,String> toReturn = new HashMap<>();
        for (KeyMovement keyMovement : keyMovementKeyCodeMap.keySet()) {
            toReturn.put(keyMovement, keyMovementKeyCodeMap.get(keyMovement).getName());
        }
        return toReturn;
    }
}
