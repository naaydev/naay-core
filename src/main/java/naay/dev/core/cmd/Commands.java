package naay.dev.core.cmd;

import naay.dev.core.Main;
import naay.dev.core.database.Database;
import naay.dev.core.server.ServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    private static final List<Command> commands = new ArrayList<>();

    public static void setupCommands() {
        Main.instance.getCommand("nick").setExecutor(new Commands());
        Main.instance.getCommand("fly").setExecutor(new Commands());
        Main.instance.getCommand("gamemode").setExecutor(new Commands());
        Main.instance.getCommand("heal").setExecutor(new Commands());
        Main.instance.getCommand("feed").setExecutor(new Commands());
        Main.instance.getCommand("god").setExecutor(new Commands());
        Main.instance.getCommand("vanish").setExecutor(new Commands());
        Main.instance.getCommand("clear").setExecutor(new Commands());
        Main.instance.getCommand("speed").setExecutor(new Commands());
        Main.instance.getCommand("skins").setExecutor(new Commands());
        Main.instance.getCommand("coins").setExecutor(new Commands());
        Main.instance.getCommand("cash").setExecutor(new Commands());
        Main.instance.getCommand("tell").setExecutor(new Commands());
        Main.instance.getCommand("reply").setExecutor(new Commands());
        Main.instance.getCommand("ping").setExecutor(new Commands());
        Main.instance.getCommand("spawn").setExecutor(new Commands());
        Main.instance.getCommand("lobby").setExecutor(new Commands());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && !sender.isOp()) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        switch (cmd.getName().toLowerCase()) {
            case "nick":
                return handleNick(sender, args);
            case "fly":
                return handleFly(sender);
            case "gamemode":
                return handleGameMode(sender, args);
            case "heal":
                return handleHeal(sender);
            case "feed":
                return handleFeed(sender);
            case "god":
                return handleGod(sender);
            case "vanish":
                return handleVanish(sender);
            case "clear":
                return handleClear(sender);
            case "speed":
                return handleSpeed(sender, args);
            case "skins":
                return handleSkins(sender);
            case "coins":
                return handleCoins(sender, args);
            case "cash":
                return handleCash(sender, args);
            case "tell":
                return handleTell(sender, args);
            case "reply":
                return handleReply(sender, args);
            case "ping":
                return handlePing(sender);
            case "spawn":
                return handleSpawn(sender);
            case "lobby":
                return handleLobby(sender);
        }
        return true;
    }

    private boolean handleNick(CommandSender sender, String[] args) {
        if (!sender.hasPermission("naay.core.nick")) {
            sender.sendMessage("§cSem permissão.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("§cUse: /nick <nick>");
            return true;
        }
        sender.sendMessage("§aNick alterado para §e" + args[0]);
        return true;
    }

    private boolean handleFly(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        p.setAllowFlight(!p.getAllowFlight());
        sender.sendMessage("§aFly: " + (p.getAllowFlight() ? "§aAtivado" : "§cDesativado"));
        return true;
    }

    private boolean handleGameMode(CommandSender sender, String[] args) {
        if (!sender.hasPermission("naay.core.gamemode")) {
            sender.sendMessage("§cSem permissão.");
            return true;
        }
        if (!(sender instanceof Player)) return true;
        if (args.length == 0) {
            sender.sendMessage("§cUse: /gm <0|1|2|3>");
            return true;
        }
        Player p = (Player) sender;
        try {
            int mode = Integer.parseInt(args[0]);
            p.setGameMode(org.bukkit.GameMode.values()[mode]);
            sender.sendMessage("§aGameMode alterado!");
        } catch (Exception e) {
            sender.sendMessage("§cModo inválido.");
        }
        return true;
    }

    private boolean handleHeal(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        sender.sendMessage("§aVida restaurada!");
        return true;
    }

    private boolean handleFeed(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        p.setFoodLevel(20);
        sender.sendMessage("§aFome restaurada!");
        return true;
    }

    private boolean handleGod(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        sender.sendMessage("§aGodmode!");
        return true;
    }

    private boolean handleVanish(CommandSender sender) {
        if (!sender.hasPermission("naay.core.vanish")) {
            sender.sendMessage("§cSem permissão.");
            return true;
        }
        sender.sendMessage("§aVanish!");
        return true;
    }

    private boolean handleClear(CommandSender sender) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        p.getInventory().clear();
        sender.sendMessage("§aInventário limpo!");
        return true;
    }

    private boolean handleSpeed(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (args.length == 0) {
            sender.sendMessage("§cUse: /speed <1-10>");
            return true;
        }
        try {
            float speed = Float.parseFloat(args[0]);
            ((Player) sender).setWalkSpeed(Math.min(speed / 10f, 1f));
            sender.sendMessage("§aVelocidade: " + speed);
        } catch (Exception e) {
            sender.sendMessage("§cValor inválido.");
        }
        return true;
    }

    private boolean handleSkins(CommandSender sender) {
        sender.sendMessage("§aSk_available: steve, hero, zombie, skeleton");
        return true;
    }

    private boolean handleCoins(CommandSender sender, String[] args) {
        if (args.length > 0 && sender.hasPermission("naay.core.coins")) {
            sender.sendMessage("§aCoins adjustments are now §eper-player§a!");
        } else {
            if (sender instanceof Player) {
                sender.sendMessage("§aYou have §e0 §acoins!");
            }
        }
        return true;
    }

    private boolean handleCash(CommandSender sender, String[] args) {
        if (args.length > 0 && sender.hasPermission("naay.core.cash")) {
            sender.sendMessage("§aCash adjustments are now §eper-player§a!");
        } else {
            if (sender instanceof Player) {
                sender.sendMessage("§aYou have §e0 §acash!");
            }
        }
        return true;
    }

    private boolean handleTell(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (args.length < 2) {
            sender.sendMessage("§cUse: /tell <player> <msg>");
            return true;
        }
        Player target = Main.instance.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cJogador offline.");
            return true;
        }
        String msg = join(args, 1);
        sender.sendMessage("§eYou §8➜ §e" + target.getName() + "§8: §f" + msg);
        target.sendMessage("§e" + ((Player) sender).getName() + " §8➜ §eYou§8: §f" + msg);
        Main.reply.put(target, (Player) sender);
        return true;
    }

    private boolean handleReply(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player target = Main.reply.get((Player) sender);
        if (target == null || !target.isOnline()) {
            sender.sendMessage("§cNobody to reply to.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§cUse: /r <msg>");
            return true;
        }
        String msg = join(args, 0);
        sender.sendMessage("§eYou §8➜ §e" + target.getName() + "§8: §f" + msg);
        target.sendMessage("§e" + ((Player) sender).getName() + " §8➜ §eYou§8: §f" + msg);
        return true;
    }

    private boolean handlePing(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage("§aYour ping: §e" + getPing((Player) sender) + "ms");
        }
        return true;
    }

    private boolean handleSpawn(CommandSender sender) {
        if (Main.getLobby() != null && sender instanceof Player) {
            ((Player) sender).teleport(Main.getLobby());
            sender.sendMessage("§aTeleporting to spawn...");
        }
        return true;
    }

    private boolean handleLobby(CommandSender sender) {
        if (sender instanceof Player) {
            Main.sendServer((Player) sender, "Lobby-1");
        }
        return true;
    }

    private String join(String[] args, int start) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        return sb.toString().trim();
    }

    private int getPing(Player p) {
        try {
            java.lang.reflect.Method getHandle = p.getClass().getMethod("getHandle");
            Object handle = getHandle.invoke(p);
            java.lang.reflect.Field pingField = handle.getClass().getDeclaredField("ping");
            pingField.setAccessible(true);
            return pingField.getInt(handle);
        } catch (Exception e) {
            return 0;
        }
    }
}