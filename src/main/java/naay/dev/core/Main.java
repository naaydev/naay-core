package naay.dev.core;

import naay.dev.core.cmd.Commands;
import naay.dev.core.database.Database;
import naay.dev.core.database.MySQLDatabase;
import naay.dev.core.holograms.HologramManager;
import naay.dev.core.listeners.Listeners;
import naay.dev.core.npc.NPCManager;
import naay.dev.core.player.Profile;
import naay.dev.core.role.Role;
import naay.dev.core.scoreboard.ScoreboardManager;
import naay.dev.core.server.ServerManager;
import naay.dev.core.updates.UpdatesManager;
import naay.dev.core.utils.BukkitUtils;
import naay.dev.core.utils.QueueManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public static Main instance;
    public static boolean validInit;
    public static Location lobby;
    public static String minigame = "";
    public static HashMap<Player, Player> reply = new HashMap<>();

    public static List<String> warnings = new ArrayList<>();
    public static List<String> minigames = Arrays.asList("SkyWars", "TheBridge", "Murder", "BedWars", "BuildBattle");

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        lobby = Bukkit.getWorlds().get(0).getSpawnLocation();

        if (Bukkit.getSpawnRadius() != 0) {
            Bukkit.setSpawnRadius(0);
        }

        if (!checkBlacklist()) {
            return;
        }

        if (!removeReloadCommand()) {
            return;
        }

        if (!checkPlaceholderAPI()) {
            return;
        }

        setupDatabase();
        NPCManager.setup();
        HologramManager.setup();
        Role.setupRoles();
        ScoreboardManager.setup();
        ServerManager.setupServers();
        UpdatesManager.setup();
        Commands.setupCommands();
        Listeners.setupListeners();

        Bukkit.getServer().getMessenger().registerOutgoingPluginMessage(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerOutgoingPluginMessage(this, "NaayCore");
        Bukkit.getServer().getMessenger().registerIncomingPluginMessage(this, "NaayCore", new NaayChannel());

        validInit = true;
        getLogger().info("NaayCore ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        if (validInit) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Profile profile = Profile.unloadProfile(player.getName());
                if (profile != null) {
                    profile.saveSync();
                    profile.destroy();
                }
            }
            Database.close();
        }
        getLogger().info("NaayCore desativado.");
    }

    private boolean checkBlacklist() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResource("blacklist.txt"), StandardCharsets.UTF_8))) {
            String plugin;
            while ((plugin = reader.readLine()) != null) {
                if (Bukkit.getPluginManager().getPlugin(plugin.split(" ")[0]) != null) {
                    warnings.add(" - " + plugin);
                }
            }
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Cannot load blacklist.txt", ex);
        }

        if (!warnings.isEmpty()) {
            CommandSender sender = Bukkit.getConsoleSender();
            sender.sendMessage(" §6§lAVISO IMPORTANTE§r §7Plugins conflitando com NaayCore:");
            for (String warning : warnings) {
                sendMessage("§f" + warning);
            }
            Bukkit.shutdown();
            return false;
        }
        return true;
    }

    private boolean removeReloadCommand() {
        try {
            SimpleCommandMap commandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            Field field = commandMap.getClass().getDeclaredField("knownCommands");
            field.setAccessible(true);
            Map<String, Command> knownCommands = (Map<String, Command>) field.get(commandMap);
            knownCommands.remove("reload");
            knownCommands.remove("rl");
            knownCommands.remove("plugins");
            knownCommands.remove("plugin");
            knownCommands.remove("pl");
            knownCommands.remove("ver");
            knownCommands.remove("about");
            knownCommands.remove("stop");
            knownCommands.remove("restart");
        } catch (ReflectiveOperationException ex) {
            getLogger().log(Level.SEVERE, "Cannot remove reload command", ex);
        }
        return true;
    }

    private boolean checkPlaceholderAPI() {
        if (PlaceholderAPIPlugin.getInstance() == null ||
            !PlaceholderAPIPlugin.getInstance().getDescription().getVersion().equals("2.11.5")) {
            getLogger().warning("Use PlaceholderAPI v2.11.5");
            return false;
        }
        PlaceholderAPI.registerExpansion(new NaayExpansion());
        return true;
    }

    private void setupDatabase() {
        String type = getConfig().getString("database.type", "mysql");
        if (type.equalsIgnoreCase("mysql")) {
            Database.setup(
                getConfig().getString("database.mysql.host"),
                getConfig().getString("database.mysql.port"),
                getConfig().getString("database.mysql.database"),
                getConfig().getString("database.mysql.user"),
                getConfig().getString("database.mysql.password")
            );
        }
    }

    public static void sendServer(Player player, String server) {
        player.closeInventory();
        QueueManager.addToQueue(player, server);
    }

    public static void setLobby(Location location) {
        lobby = location;
    }

    public static Location getLobby() {
        return lobby;
    }

    public static Main getInstance() {
        return instance;
    }
}