package naay.dev.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class BukkitUtils {

    public static Location deserializeLocation(String serialized) {
        if (serialized == null || serialized.isEmpty()) return null;
        try {
            String[] parts = serialized.split(",");
            World world = Bukkit.getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = parts.length > 4 ? Float.parseFloat(parts[4]) : 0;
            float pitch = parts.length > 5 ? Float.parseFloat(parts[5]) : 0;
            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception e) {
            return null;
        }
    }

    public static String serializeLocation(Location loc) {
        if (loc == null) return "";
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }

    public static Location getCenter(Location loc) {
        return loc.clone().add(0.5, 0, 0.5);
    }

    public static Vector getDirection(Location from, Location to) {
        return to.clone().subtract(from).toVector().normalize();
    }
}