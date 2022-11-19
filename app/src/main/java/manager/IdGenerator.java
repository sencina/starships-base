package manager;

public class IdGenerator {

    public static int currentId = 0;

    public static String generateId() {
        return String.valueOf(currentId++);
    }

}
