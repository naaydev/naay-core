package naay.dev.core.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import naay.dev.core.player.Profile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class NaayExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "naay";
    }

    @Override
    public String getAuthor() {
        return "NaayDev";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (player == null) return "";
        
        Profile profile = Profile.getProfile(player.getName());
        if (profile == null) return "";

        switch (identifier.toLowerCase()) {
            case "coins":
                return String.valueOf(profile.getCoins());
            case "cash":
                return String.valueOf(profile.getCash());
            case "kills":
                return String.valueOf(profile.getKills());
            case "deaths":
                return String.valueOf(profile.getDeaths());
            case "wins":
                return String.valueOf(profile.getWins());
            case "level":
                return String.valueOf(profile.getLevel());
            case "xp":
                return String.valueOf(profile.getXp());
            default:
                return null;
        }
    }
}