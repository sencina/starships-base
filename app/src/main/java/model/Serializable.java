package model;

import org.json.simple.JSONObject;

public interface Serializable<T> {

    JSONObject toJson();
}
