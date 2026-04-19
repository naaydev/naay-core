package naay.dev.core;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class NaayChannel implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("NaayCore")) return;
        
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(message);
            DataInputStream in = new DataInputStream(stream);
            String subChannel = in.readUTF();
            
            switch (subChannel) {
                case "UpdateCoins":
                    handleUpdateCoins(player, in);
                    break;
                case "UpdateCash":
                    handleUpdateCash(player, in);
                    break;
                case "UpdateXP":
                    handleUpdateXP(player, in);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateCoins(Player player, DataInputStream in) throws IOException {
        int amount = in.readInt();
        player.sendMessage("§aYou received §e" + amount + " coins!");
    }

    private void handleUpdateCash(Player player, DataInputStream in) throws IOException {
        int amount = in.readInt();
        player.sendMessage("§aYou received §e" + amount + " cash!");
    }

    private void handleUpdateXP(Player player, DataInputStream in) throws IOException {
        int amount = in.readInt();
        player.sendMessage("§aYou received §e" + amount + " XP!");
    }
}