package state;

import javafx.scene.input.KeyCode;
import model.KeyAction;

import java.util.List;
import java.util.Map;

public class KeyMapper {

    private final Map<String, List<KeyAction>> keyMap;

    public KeyMapper(Map<String, List<KeyAction>> keyMap) {
        this.keyMap = keyMap;
    }

    public KeyAction getAction(String key, KeyCode code) {
        if (keyMap.containsKey(key)){
            return keyMap.get(key).stream().filter(action -> action.key().equals(code)).findFirst().orElseThrow();
        }
        throw new IllegalArgumentException("Key not found");
    }

    public KeyMapper setAction(String key, KeyAction action){
        List<KeyAction> updatedList = updateList(List.copyOf(keyMap.get(key)), action);
        Map<String, List<KeyAction>> updatedMap = new java.util.HashMap<>(Map.copyOf(keyMap));
        updatedMap.put(key, updatedList);
        return new KeyMapper(updatedMap);
    }

    private List<KeyAction> updateList(List<KeyAction> list, KeyAction action){
        List<KeyAction> toReturn = new java.util.ArrayList<>(list.stream().filter(keyAction -> !keyAction.key().equals(action.key())).toList());
        toReturn.add(action);
        return toReturn;
    }
}
