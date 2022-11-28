package model;

import org.json.simple.JSONObject;

public record Tuple<K,V>(K first, V second) implements Serializable {
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", first);
        jsonObject.put("points", second);
        return jsonObject;
    }
}