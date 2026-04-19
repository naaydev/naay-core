package naay.dev.core.npc;

import naay.dev.core.Main;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPCManager {

    private static final List<NPC> npcs = new ArrayList<>();
    private static final Map<Integer, NPC> npcMap = new HashMap<>();

    public static void setup() {
    }

    public static NPC createNPC(Location loc, String name, String server) {
        Location spawn = loc.clone().add(0, 1000, 0);
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(spawn, EntityType.WITCH);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.setGravity(false);
        entity.setSilent(true);
        entity.setInvulnerable(true);
        NPC npc = new NPC(entity, name, server);
        npcs.add(npc);
        npcMap.put(entity.getEntityId(), npc);
        return npc;
    }

    public static void removeNPC(int entityId) {
        NPC npc = npcMap.remove(entityId);
        if (npc != null) {
            npc.getEntity().remove();
            npcs.remove(npc);
        }
    }

    public static void onClick(Player player, int entityId) {
        NPC npc = npcMap.get(entityId);
        if (npc != null) {
            Main.sendServer(player, npc.getServer());
        }
    }

    public static class NPC {
        private final LivingEntity entity;
        private final String name;
        private final String server;

        public NPC(LivingEntity entity, String name, String server) {
            this.entity = entity;
            this.name = name;
            this.server = server;
        }

        public LivingEntity getEntity() {
            return entity;
        }

        public String getName() {
            return name;
        }

        public String getServer() {
            return server;
        }
    }
}