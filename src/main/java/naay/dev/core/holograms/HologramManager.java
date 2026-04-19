package naay.dev.core.holograms;

import naay.dev.core.Main;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramManager {

    private static final Map<Integer, Hologram> holograms = new HashMap<>();

    public static void setup() {
    }

    public static void createHologram(Location loc, String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            Location standLoc = loc.clone().subtract(0, i * 0.3, 0);
            ArmorStand stand = loc.getWorld().spawn(standLoc, ArmorStand.class);
            stand.setSmall(true);
            stand.setMarker(true);
            stand.setVisible(false);
            stand.setCustomName(lines[i]);
            stand.setCustomNameVisible(true);
            stand.setGravity(false);
            stand.setInvulnerable(true);
            stand.setCollidable(false);
            holograms.put(stand.getEntityId(), new Hologram(stand, lines[i]));
        }
    }

    public static class Hologram {
        private final ArmorStand stand;
        private final String text;

        public Hologram(ArmorStand stand, String text) {
            this.stand = stand;
            this.text = text;
        }

        public ArmorStand getStand() {
            return stand;
        }

        public String getText() {
            return text;
        }
    }
}