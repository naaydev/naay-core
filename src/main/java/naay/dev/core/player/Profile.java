package naay.dev.core.player;

import naay.dev.core.Main;
import naay.dev.core.database.Database;
import naay.dev.core.role.Role;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Profile {

    private static final Map<String, Profile> profiles = new HashMap<>();
    
    private final String name;
    private final Player player;
    private String uuid;
    private int coins;
    private int cash;
    private int wins;
    private int kills;
    private int deaths;
    private int level;
    private int xp;
    private Role role;
    private String coat;
    private String tag;
    
    public Profile(Player player) {
        this.player = player;
        this.name = player.getName();
    }
    
    public static void loadProfile(Player player) {
        try {
            PreparedStatement ps = Database.prepare("SELECT * FROM players WHERE nick = ?");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            
            Profile profile = new Profile(player);
            
            if (rs.next()) {
                profile.setUuid(rs.getString("uuid"));
                profile.setCoins(rs.getInt("coins"));
                profile.setCash(rs.getInt("cash"));
                profile.setWins(rs.getInt("wins"));
                profile.setKills(rs.getInt("kills"));
                profile.setDeaths(rs.getInt("deaths"));
                profile.setLevel(rs.getInt("level"));
                profile.setXp(rs.getInt("xp"));
            } else {
                ps = Database.prepare("INSERT INTO players (nick, uuid, lastlogin) VALUES (?, ?, ?)");
                ps.setString(1, player.getName());
                ps.setString(2, player.getUniqueId().toString());
                ps.setLong(3, System.currentTimeMillis());
                ps.executeUpdate();
                profile.setUuid(player.getUniqueId().toString());
            }
            
            profiles.put(player.getName(), profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Profile unloadProfile(String name) {
        return profiles.remove(name);
    }
    
    public static Profile getProfile(String name) {
        return profiles.get(name);
    }
    
    public void saveSync() {
        try {
            PreparedStatement ps = Database.prepare("UPDATE players SET coins = ?, cash = ?, wins = ?, kills = ?, deaths = ?, level = ?, xp = ? WHERE nick = ?");
            ps.setInt(1, coins);
            ps.setInt(2, cash);
            ps.setInt(3, wins);
            ps.setInt(4, kills);
            ps.setInt(5, deaths);
            ps.setInt(6, level);
            ps.setInt(7, xp);
            ps.setString(8, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void destroy() {
        profiles.remove(name);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public int getCoins() {
        return coins;
    }
    
    public void setCoins(int coins) {
        this.coins = coins;
    }
    
    public int getCash() {
        return cash;
    }
    
    public void setCash(int cash) {
        this.cash = cash;
    }
    
    public int getWins() {
        return wins;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public int getKills() {
        return kills;
    }
    
    public void setKills(int kills) {
        this.kills = kills;
    }
    
    public int getDeaths() {
        return deaths;
    }
    
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getXp() {
        return xp;
    }
    
    public void setXp(int xp) {
        this.xp = xp;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getCoat() {
        return coat;
    }
    
    public void setCoat(String coat) {
        this.coat = coat;
    }
    
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
}