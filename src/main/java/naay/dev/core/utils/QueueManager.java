package naay.dev.core.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import naay.dev.core.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class QueueManager {

    private static final Map<Player, String> queue = new HashMap<>();

    public static void addToQueue(Player player, String server) {
        queue.put(player, server);
        player.sendMessage("§aConnecting to §e" + server + "§a...");
        
        Main.instance.getServer().getScheduler().runTaskLater(Main.instance, () -> {
            if (player.isOnline()) {
                player.closeInventory();
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server);
                player.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
                queue.remove(player);
            }
        }, 20L);
    }

    public static String getQueueServer(Player player) {
        return queue.get(player);
    }

    public static boolean isInQueue(Player player) {
        return queue.containsKey(player);
    }
}