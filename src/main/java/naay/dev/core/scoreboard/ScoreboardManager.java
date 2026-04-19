package naay.dev.core.scoreboard;

import naay.dev.core.Main;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private static final Map<Player, Scoreboard> scoreboards = new HashMap<>();

    public static void setup() {
        for (Player p : Main.instance.getServer().getOnlinePlayers()) {
            createScoreboard(p);
        }
    }

    public static void createScoreboard(Player p) {
        Scoreboard board = Main.instance.getServer().getScoreboardManager().getNewScoreboard();
        Team team = board.registerNewTeam("display");
        team.addEntry(p.getName());
        team.setPrefix("§7");
        p.setScoreboard(board);
        scoreboards.put(p, board);
    }

    public static Scoreboard getScoreboard(Player p) {
        return scoreboards.get(p);
    }
}