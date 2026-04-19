package naay.dev.core.updates;

import java.util.HashMap;
import java.util.Map;

public class UpdatesManager {

    private static final Map<String, Long> updates = new HashMap<>();

    public static void setup() {
    }

    public static void addUpdate(String player, long time) {
        updates.put(player, time);
    }

    public static long getUpdate(String player) {
        return updates.getOrDefault(player, 0L);
    }

    public static boolean canUpdate(String player) {
        long lastUpdate = updates.getOrDefault(player, 0L);
        return System.currentTimeMillis() - lastUpdate >= 86400000;
    }
}