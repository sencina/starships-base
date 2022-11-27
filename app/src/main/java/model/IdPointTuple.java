package model;

import org.json.simple.JSONObject;

public record IdPointTuple(String id, int points) implements Serializable {
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("points", points);
        return jsonObject;
    }
}