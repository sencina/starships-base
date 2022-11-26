package generator;

public class IdGenerator {

    public static int currentId = 0;
    public static int currentStarshipId = 0;

    public static String generateId() {
        return String.valueOf(currentId++);
    }

    public static String generateStarshipId() {
        return String.valueOf(currentStarshipId++);
    }

}
